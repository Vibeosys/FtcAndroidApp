package com.ftcsolutions.tradenow.activities;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.ftcsolutions.tradenow.R;
import com.ftcsolutions.tradenow.data.UserDTO;
import com.ftcsolutions.tradenow.data.requestdata.BaseRequestDTO;
import com.ftcsolutions.tradenow.data.requestdata.ResetPasswordRequest;
import com.ftcsolutions.tradenow.data.responsedata.ResponseErrorDTO;
import com.ftcsolutions.tradenow.data.responsedata.ResponseLoginDTO;
import com.ftcsolutions.tradenow.utils.ServerRequestConstants;
import com.ftcsolutions.tradenow.utils.ServerSyncManager;
import com.ftcsolutions.tradenow.utils.UserAuth;

import java.security.MessageDigest;

public class ResetPassActivity extends BaseActivity implements View.OnClickListener,
        ServerSyncManager.OnSuccessResultReceived, ServerSyncManager.OnErrorResultReceived {

    private static final String TAG = ResetPassActivity.class.getSimpleName();
    private View mainResetPassView;
    private EditText txtOldPassword, txtNewPassword, txtConfirmPass;
    private Button btnUpdate, btnCancel;
    View formView, progressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass);
        setTitle(getResources().getString(R.string.reset_pass));
        mainResetPassView = findViewById(R.id.mainResetPassView);
        txtOldPassword = (EditText) findViewById(R.id.txtOldPassword);
        txtNewPassword = (EditText) findViewById(R.id.txtNewPassword);
        txtConfirmPass = (EditText) findViewById(R.id.txtConfirmPass);
        formView = findViewById(R.id.formView);
        progressView = findViewById(R.id.progressBar);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnCancel.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        mServerSyncManager.setOnStringErrorReceived(this);
        mServerSyncManager.setOnStringResultReceived(this);

    }

    @Override
    protected View getMainView() throws NullPointerException {
        return this.mainResetPassView;
    }

    public static final String getmd5(final String toEncrypt) {
        try {
            final MessageDigest digest = MessageDigest.getInstance("md5");
            digest.update(toEncrypt.getBytes());
            final byte[] bytes = digest.digest();
            final StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(String.format("%02X", bytes[i]));
            }
            return sb.toString().toLowerCase();
        } catch (Exception exc) {
            return ""; // Impossibru!
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.btnUpdate:
                String strOldPass, strNewPass, strConfirm;
                strOldPass = txtOldPassword.getText().toString();
                strNewPass = txtNewPassword.getText().toString();
                strConfirm = txtConfirmPass.getText().toString();
                callTOUpdate(strOldPass, strNewPass, strConfirm);
                break;
            case R.id.btnCancel:
                finish();
                break;
        }
    }

    private void callTOUpdate(String strOldPass, String strNewPass, String strConfirm) {
        boolean cancelFlag = false;
        View focusView = null;
        txtOldPassword.setError(null);
        txtNewPassword.setError(null);
        txtConfirmPass.setError(null);
        String encryptedPass = getmd5(strOldPass);
        if (TextUtils.isEmpty(strOldPass)) {
            cancelFlag = true;
            focusView = txtOldPassword;
            txtOldPassword.setError(getResources().getString(R.string.str_err_old_pass_empty));
        } else if (TextUtils.isEmpty(strNewPass)) {
            cancelFlag = true;
            focusView = txtNewPassword;
            txtNewPassword.setError(getResources().getString(R.string.str_err_new_pass_empty));
        } else if (TextUtils.isEmpty(strConfirm)) {
            cancelFlag = true;
            focusView = txtConfirmPass;
            txtConfirmPass.setError(getResources().getString(R.string.str_err_confirm_pass_empty));
        } else if (!encryptedPass.equals(mSessionManager.getUserPassword())) {
            cancelFlag = true;
            focusView = txtOldPassword;
            txtOldPassword.setError(getResources().getString(R.string.str_err_old_pass_wrong));
        } else if (!strNewPass.equals(strConfirm)) {
            cancelFlag = true;
            focusView = txtConfirmPass;
            txtConfirmPass.setError(getResources().getString(R.string.str_err_pass_not_match));
        } else if (strOldPass.equals(strNewPass)) {
            cancelFlag = true;
            focusView = txtNewPassword;
            txtNewPassword.setError(getResources().getString(R.string.str_err_old_new_match));
        }
        Log.d(TAG, "## old pass " + mSessionManager.getUserPassword() + "\n Encryp " + encryptedPass);
        if (cancelFlag) {
            focusView.requestFocus();
        } else {
            showProgress(true, formView, progressView);
            ResetPasswordRequest resetPasswordRequest = new ResetPasswordRequest(strOldPass, strNewPass);
            Gson gson = new Gson();
            String serializedJsonString = gson.toJson(resetPasswordRequest);
            BaseRequestDTO baseRequestDTO = new BaseRequestDTO();
            baseRequestDTO.setData(serializedJsonString);
            mServerSyncManager.uploadDataToServer(ServerRequestConstants.REQUEST_RESET_PASS,
                    mSessionManager.getResetPassUrl(), baseRequestDTO);
        }


    }

    @Override
    public void onVolleyErrorReceived(@NonNull VolleyError error, int requestToken) {
        switch (requestToken) {
            case ServerRequestConstants.REQUEST_RESET_PASS:
                showProgress(false, formView, progressView);
                Log.e(TAG, "Error in reset Password");
                Snackbar snackbar = Snackbar
                        .make(formView, "Server Error", Snackbar.LENGTH_LONG)
                        .setAction("Retry", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //startActivityForResult(new Intent(Settings.ACTION_SETTINGS), 0);
                            }
                        });
// Changing message text color
                snackbar.setActionTextColor(Color.RED);
                snackbar.show();
                break;
        }

    }

    @Override
    public void onDataErrorReceived(ResponseErrorDTO errorDbDTO, int requestToken) {
        switch (requestToken) {
            case ServerRequestConstants.REQUEST_RESET_PASS:
                showProgress(false, formView, progressView);
                Log.e(TAG, "Error in reset Password" + errorDbDTO.getMessage());
                Snackbar snackbar = Snackbar
                        .make(formView, errorDbDTO.getMessage(), Snackbar.LENGTH_INDEFINITE)
                        .setAction("Retry", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //startActivityForResult(new Intent(Settings.ACTION_SETTINGS), 0);
                            }
                        });
// Changing message text color
                snackbar.setActionTextColor(Color.RED);
                snackbar.show();
                break;
        }
    }

    @Override
    public void onResultReceived(@NonNull String data, int requestToken) {
        switch (requestToken) {
            case ServerRequestConstants.REQUEST_RESET_PASS:
                showProgress(false, formView, progressView);
                Log.d(TAG, "Success Login " + data);
                ResponseLoginDTO loginDTO = ResponseLoginDTO.deserializeJson(data);
                UserDTO userDTO = new UserDTO(loginDTO.getUserId(), loginDTO.getFullName(),
                        loginDTO.getUsername(), loginDTO.getPwd(), loginDTO.getEmail(), loginDTO.getSubscriberId());
                UserAuth userAuth = new UserAuth();
                userAuth.saveAuthenticationInfo(userDTO, getApplicationContext());
                Toast.makeText(getApplicationContext(), "Password changed successfully", Toast.LENGTH_LONG).show();
                clearFields();
                break;
        }
    }

    private void clearFields() {
        txtOldPassword.setText(null);
        txtNewPassword.setText(null);
        txtConfirmPass.setText(null);
        txtOldPassword.setError(null);
        txtNewPassword.setError(null);
        txtConfirmPass.setError(null);
    }
}
