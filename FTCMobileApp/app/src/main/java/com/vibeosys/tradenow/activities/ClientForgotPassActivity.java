package com.vibeosys.tradenow.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.vibeosys.tradenow.R;
import com.vibeosys.tradenow.data.requestdata.BaseRequestDTO;
import com.vibeosys.tradenow.data.requestdata.ForgotPasswordRequest;
import com.vibeosys.tradenow.data.requestdata.ForgotSubPasswordRequest;
import com.vibeosys.tradenow.data.responsedata.ResponseErrorDTO;
import com.vibeosys.tradenow.utils.NetworkUtils;
import com.vibeosys.tradenow.utils.ServerRequestConstants;
import com.vibeosys.tradenow.utils.ServerSyncManager;
import com.vibeosys.tradenow.utils.Validator;

public class ClientForgotPassActivity extends BaseActivity implements View.OnClickListener, ServerSyncManager.OnErrorResultReceived, ServerSyncManager.OnSuccessResultReceived {

    private EditText txtEmail, txtUserName, txtSubId;
    private Button btnForgotPass;
    private View mainForgotPassword;
    private String strEmail, strUserName, strSubId;
    private Validator validator = new Validator();
    private View formView, progressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_forgot_pass);
        setTitle(getResources().getString(R.string.str_activity_forgot_password));
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtUserName = (EditText) findViewById(R.id.txtUserName);
        txtSubId = (EditText) findViewById(R.id.txtSubId);
        btnForgotPass = (Button) findViewById(R.id.btnForgotPass);
        mainForgotPassword = findViewById(R.id.mainForgotPassword);
        formView = findViewById(R.id.forgotPassword);
        progressView = findViewById(R.id.progressBarforgot);
        btnForgotPass.setOnClickListener(this);
        mServerSyncManager.setOnStringErrorReceived(this);
        mServerSyncManager.setOnStringResultReceived(this);
    }

    @Override
    protected View getMainView() throws NullPointerException {
        return mainForgotPassword;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnForgotPass:
                strEmail = txtEmail.getText().toString();
                strUserName = txtUserName.getText().toString();
                strSubId = txtSubId.getText().toString();
                if (NetworkUtils.isActiveNetworkAvailable(getApplicationContext()))
                    callForgotPass(strEmail, strUserName, strSubId);
                else
                    createNetworkAlertDialog("Network Error", "Please connect to the Internet");
                break;
        }

    }

    private void callForgotPass(String strEmail, String userName, String strSubId) {
        boolean cancelFlag = false;
        View focusView = null;
        txtEmail.setError(null);
        if (TextUtils.isEmpty(userName)) {
            cancelFlag = true;
            focusView = txtUserName;
            txtUserName.setError(getResources().getString(R.string.str_error_user_name_empty));
        } else if (!validator.validateUserName(userName)) {
            cancelFlag = true;
            focusView = txtUserName;
            txtUserName.setError(getResources().getString(R.string.str_error_user_name_validate));
        } else if (TextUtils.isEmpty(strEmail)) {
            cancelFlag = true;
            focusView = txtEmail;
            txtEmail.setError(getResources().getString(R.string.str_err_email_empty));
        } else if (!validator.isValidMail(strEmail)) {
            cancelFlag = true;
            focusView = txtEmail;
            txtEmail.setError(getResources().getString(R.string.str_err_email_validate));
        } else if (TextUtils.isEmpty(strSubId)) {
            cancelFlag = true;
            focusView = txtSubId;
            txtSubId.setError(getResources().getString(R.string.str_err_sub_empty));
        }
        if (cancelFlag) {
            focusView.requestFocus();
        } else {
            try {
                long subId = Long.parseLong(strSubId);
                showProgress(true, formView, progressView);
                ForgotSubPasswordRequest forgotPasswordRequest = new ForgotSubPasswordRequest(userName, strEmail, subId);
                Gson gson = new Gson();
                String serializedJsonString = gson.toJson(forgotPasswordRequest);
                BaseRequestDTO baseRequestDTO = new BaseRequestDTO();
                baseRequestDTO.setData(serializedJsonString);
                mServerSyncManager.uploadDataToServer(ServerRequestConstants.REQUEST_FORGOT_CLIENT_PASS,
                        mSessionManager.getForgotPassClientUrl(), baseRequestDTO);
            } catch (Exception e) {
                txtSubId.requestFocus();
                txtSubId.setError(getResources().getString(R.string.str_err_sub_num_invalid));
            }

        }

    }

    @Override
    public void onVolleyErrorReceived(@NonNull VolleyError error, int requestToken) {
        switch (requestToken) {
            case ServerRequestConstants.REQUEST_FORGOT_CLIENT_PASS:
                showProgress(false, formView, progressView);
                customAlterDialog(getResources().getString(R.string.str_err_server_err),
                        getResources().getString(R.string.str_err_server_msg));
                break;
        }
    }

    @Override
    public void onDataErrorReceived(ResponseErrorDTO errorDbDTO, int requestToken) {
        switch (requestToken) {
            case ServerRequestConstants.REQUEST_FORGOT_CLIENT_PASS:
                showProgress(false, formView, progressView);
                customAlterDialog("Warning", errorDbDTO.getMessage());
                break;
        }
    }

    @Override
    public void onResultReceived(@NonNull String data, int requestToken) {
        switch (requestToken) {
            case ServerRequestConstants.REQUEST_FORGOT_CLIENT_PASS:
                showProgress(false, formView, progressView);
                customSuccessDialog(getResources().getString(R.string.str_dlg_result_forgot_pass),
                        getResources().getString(R.string.str_msg_pass));
                break;
        }
    }

    protected void customSuccessDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("" + title);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });
        builder.show();
    }
}
