package com.vibeosys.tradenow.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.vibeosys.tradenow.R;
import com.vibeosys.tradenow.data.requestdata.BaseRequestDTO;
import com.vibeosys.tradenow.data.requestdata.RegisterUserRequest;
import com.vibeosys.tradenow.data.requestdata.UsernameAvailabilityRequest;
import com.vibeosys.tradenow.data.responsedata.ResponseErrorDTO;
import com.vibeosys.tradenow.utils.NetworkUtils;
import com.vibeosys.tradenow.utils.ServerRequestConstants;
import com.vibeosys.tradenow.utils.ServerSyncManager;
import com.vibeosys.tradenow.utils.Validator;

public class RegisterActivity extends BaseActivity implements View.OnClickListener,
        ServerSyncManager.OnSuccessResultReceived, ServerSyncManager.OnErrorResultReceived {

    private static final String TAG = RegisterActivity.class.getSimpleName();
    private EditText txtName, txtUserName, txtEmailId, txtPassword, txtPhone;
    private Button btnCheck, btnRegister;
    private TextView txtAvailStatus;
    private Validator validator = new Validator();
    private ProgressBar progressCheck;
    private String mStrUserName, mStrName, mStrPassword, mStrEmailId, mStrPhone;
    private boolean checkUserFlag = false;
    private View progressBar, registerForm;
    private final int RUNTIME_CHECK_NAME = 1;
    private final int CHECK_NAME = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle(getResources().getString(R.string.str_create_acc));

        txtName = (EditText) findViewById(R.id.txtName);
        txtUserName = (EditText) findViewById(R.id.txtUserName);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtEmailId = (EditText) findViewById(R.id.txtEmail);
        txtPhone = (EditText) findViewById(R.id.txtPhone);

        btnCheck = (Button) findViewById(R.id.btnAvailablityCheck);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        txtAvailStatus = (TextView) findViewById(R.id.txtAvailStatus);
        progressCheck = (ProgressBar) findViewById(R.id.progressCheck);
        progressBar = findViewById(R.id.progressBar);
        registerForm = findViewById(R.id.formRegister);
        btnCheck.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        mServerSyncManager.setOnStringResultReceived(this);
        mServerSyncManager.setOnStringErrorReceived(this);
    }

    @Override
    protected View getMainView() throws NullPointerException {
        return registerForm;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnRegister:
                mStrName = txtName.getText().toString();
                mStrEmailId = txtEmailId.getText().toString();
                mStrPassword = txtPassword.getText().toString();
                mStrPhone = txtPhone.getText().toString();
                if (NetworkUtils.isActiveNetworkAvailable(getApplicationContext()))
                    callRegister(mStrName, mStrEmailId, mStrPassword, mStrPhone);
                else
                    createNetworkAlertDialog("Network Alert", "Please check your data connection for registration");
                break;
            case R.id.btnAvailablityCheck:
                mStrUserName = txtUserName.getText().toString();
                if (NetworkUtils.isActiveNetworkAvailable(getApplicationContext()))
                    checkAvailable(mStrUserName, CHECK_NAME);
                else
                    createNetworkAlertDialog("Network Alert", "Please check your data connection for registration");
                break;
        }
    }

    private void callRegister(String mStrName, String mStrEmailId, String mStrPassword, String mStrPhone) {
        boolean cancelFlag = false;
        View focusView = null;
        txtName.setError(null);
        txtEmailId.setError(null);
        txtPassword.setError(null);
        txtPhone.setError(null);

        if (TextUtils.isEmpty(mStrName)) {
            cancelFlag = true;
            focusView = txtName;
            txtName.setError(getResources().getString(R.string.str_err_name_empty));
        } else if (TextUtils.isEmpty(mStrEmailId)) {
            cancelFlag = true;
            focusView = txtEmailId;
            txtEmailId.setError(getResources().getString(R.string.str_err_email_empty));
        } else if (TextUtils.isEmpty(mStrPassword)) {
            cancelFlag = true;
            focusView = txtPassword;
            txtPassword.setError(getResources().getString(R.string.str_err_pass_empty));
        } else if (TextUtils.isEmpty(mStrPhone)) {
            cancelFlag = true;
            focusView = txtPhone;
            txtPhone.setError(getResources().getString(R.string.str_err_phone_empty));
        } else if (!validator.isValidMail(mStrEmailId)) {
            cancelFlag = true;
            focusView = txtEmailId;
            txtEmailId.setError(getResources().getString(R.string.str_err_email_validate));
        } else if (!validator.isValidPhone(mStrPhone)) {
            cancelFlag = true;
            focusView = txtPhone;
            txtPhone.setError(getResources().getString(R.string.str_err_phone_validate));
        } else if (!checkUserFlag) {
            cancelFlag = true;
            focusView = txtUserName;
            mStrUserName = txtUserName.getText().toString();
            checkAvailable(mStrUserName, RUNTIME_CHECK_NAME);
            txtUserName.setEnabled(true);
            // txtUserName.setError(getResources().getString(R.string.str_error_user_name_validate));
        }

        if (cancelFlag) {
            focusView.requestFocus();
        } else {
            showProgress(true, registerForm, progressBar);
            RegisterUserRequest registerUserRequest = new RegisterUserRequest(mStrUserName,
                    mStrName, mStrPassword, mStrEmailId, mStrPhone);
            Gson gson = new Gson();
            String serializedJsonString = gson.toJson(registerUserRequest);
            BaseRequestDTO baseRequestDTO = new BaseRequestDTO();
            baseRequestDTO.setData(serializedJsonString);
            mServerSyncManager.uploadDataToServer(ServerRequestConstants.REQUEST_REGISTER_USER,
                    mSessionManager.getRegisterUserUrl(), baseRequestDTO);
        }
    }

    private void checkAvailable(String userName, int flag) {

        boolean cancelFlag = false;
        View focusView = null;
        txtUserName.setError(null);

        if (TextUtils.isEmpty(userName)) {
            cancelFlag = true;
            focusView = txtUserName;
            txtUserName.setError(getResources().getString(R.string.str_error_user_name_empty));
        } else if (!validator.validateUserName(userName)) {
            cancelFlag = true;
            focusView = txtUserName;
            txtUserName.setError(getResources().getString(R.string.str_error_user_name_validate));
        }

        if (cancelFlag) {
            focusView.requestFocus();
        } else {
            progressCheck.setVisibility(View.VISIBLE);
            UsernameAvailabilityRequest checkUser = new UsernameAvailabilityRequest(userName);
            Gson gson = new Gson();
            String serializedJsonString = gson.toJson(checkUser);
            BaseRequestDTO baseRequestDTO = new BaseRequestDTO();
            baseRequestDTO.setData(serializedJsonString);
            if (flag == CHECK_NAME) {
                mServerSyncManager.uploadDataToServer(ServerRequestConstants.REQUEST_USER_NAME_AVAIL,
                        mSessionManager.getAvailableUserUrl(), baseRequestDTO);
            } else if (flag == RUNTIME_CHECK_NAME) {
                mServerSyncManager.uploadDataToServer(ServerRequestConstants.REQUEST_RUNTIME_USER_NAME_AVAIL,
                        mSessionManager.getAvailableUserUrl(), baseRequestDTO);
            }
        }

    }

    @Override
    public void onVolleyErrorReceived(@NonNull VolleyError error, int requestToken) {
        switch (requestToken) {
            case ServerRequestConstants.REQUEST_USER_NAME_AVAIL:
                progressCheck.setVisibility(View.GONE);
                customAlterDialog(getResources().getString(R.string.str_err_server_err),
                        getResources().getString(R.string.str_err_server_msg));
                Log.e(TAG, "Error at check User Name" + error.toString());
                break;
            case ServerRequestConstants.REQUEST_REGISTER_USER:
                showProgress(false, registerForm, progressBar);
                customAlterDialog(getResources().getString(R.string.str_err_server_err),
                        getResources().getString(R.string.str_err_server_msg));
                Log.e(TAG, "Error at check User Name" + error.toString());
                break;
            case ServerRequestConstants.REQUEST_RUNTIME_USER_NAME_AVAIL:
                progressCheck.setVisibility(View.GONE);
                customAlterDialog(getResources().getString(R.string.str_err_server_err),
                        getResources().getString(R.string.str_err_server_msg));
                Log.e(TAG, "Error at check User Name" + error.toString());
                break;
        }
    }

    @Override
    public void onDataErrorReceived(ResponseErrorDTO errorDbDTO, int requestToken) {
        switch (requestToken) {
            case ServerRequestConstants.REQUEST_USER_NAME_AVAIL:
                progressCheck.setVisibility(View.GONE);
                Log.e(TAG, "Error at check User Name" + errorDbDTO.getMessage());
                txtAvailStatus.setText(errorDbDTO.getMessage());
                txtAvailStatus.setTextColor(getResources().getColor(R.color.colorAccent));
                break;
            case ServerRequestConstants.REQUEST_REGISTER_USER:
                showProgress(false, registerForm, progressBar);
                Log.e(TAG, "Error at check User Name" + errorDbDTO.getMessage());
                break;
            case ServerRequestConstants.REQUEST_RUNTIME_USER_NAME_AVAIL:
                progressCheck.setVisibility(View.GONE);
                Log.e(TAG, "Error at check User Name" + errorDbDTO.getMessage());
                txtAvailStatus.setText(errorDbDTO.getMessage());
                txtAvailStatus.setTextColor(getResources().getColor(R.color.colorAccent));
                break;
        }
    }

    @Override
    public void onResultReceived(@NonNull String data, int requestToken) {
        switch (requestToken) {
            case ServerRequestConstants.REQUEST_USER_NAME_AVAIL:
                progressCheck.setVisibility(View.GONE);
                Log.d(TAG, "Success result" + data);
                checkUserFlag = true;
                txtAvailStatus.setText("User name is available");
                txtUserName.setEnabled(false);
                txtAvailStatus.setTextColor(getResources().getColor(R.color.textAlert));
                btnCheck.setVisibility(View.GONE);
                break;
            case ServerRequestConstants.REQUEST_REGISTER_USER:
                showProgress(false, registerForm, progressBar);
                Log.e(TAG, "Error at check User Name" + data);
                Toast.makeText(getApplicationContext(), getResources().
                        getString(R.string.str_register_success), Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
                break;
            case ServerRequestConstants.REQUEST_RUNTIME_USER_NAME_AVAIL:
                progressCheck.setVisibility(View.GONE);
                Log.d(TAG, "Success result" + data);
                checkUserFlag = true;
                txtAvailStatus.setText("User name is available");
                txtUserName.setEnabled(false);
                txtAvailStatus.setTextColor(getResources().getColor(R.color.textAlert));
                btnCheck.setVisibility(View.GONE);
                mStrName = txtName.getText().toString();
                mStrEmailId = txtEmailId.getText().toString();
                mStrPassword = txtPassword.getText().toString();
                mStrPhone = txtPhone.getText().toString();
                if (NetworkUtils.isActiveNetworkAvailable(getApplicationContext()))
                    callRegister(mStrName, mStrEmailId, mStrPassword, mStrPhone);
                else
                    createNetworkAlertDialog("Network Alert", "Please check your data connection for registration");
                break;
        }
    }
}
