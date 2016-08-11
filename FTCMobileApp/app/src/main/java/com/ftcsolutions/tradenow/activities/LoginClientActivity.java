package com.ftcsolutions.tradenow.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.ftcsolutions.tradenow.MainActivity;
import com.ftcsolutions.tradenow.R;
import com.ftcsolutions.tradenow.data.requestdata.BaseRequestDTO;
import com.ftcsolutions.tradenow.data.requestdata.GetUserSubLogin;
import com.ftcsolutions.tradenow.data.responsedata.ResponseErrorDTO;
import com.ftcsolutions.tradenow.utils.OneSignalIdHandler;
import com.ftcsolutions.tradenow.utils.ServerRequestConstants;
import com.ftcsolutions.tradenow.utils.ServerSyncManager;

public class LoginClientActivity extends BaseActivity implements View.OnClickListener,
        ServerSyncManager.OnErrorResultReceived, ServerSyncManager.OnSuccessResultReceived {

    private static final String TAG = LoginClientActivity.class.getSimpleName();
    private EditText txtUserName, txtPassword, txtSubscriberId;
    private TextView txtForgotPass;
    private Button btnLogIn;
    private View mainClientLoginView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_client);
        setTitle(getResources().getString(R.string.str_activity_subscriber_login));

        mainClientLoginView = findViewById(R.id.mainClientLoginView);
        txtUserName = (EditText) findViewById(R.id.txtUserName);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtSubscriberId = (EditText) findViewById(R.id.txtSubscriberId);
        txtForgotPass = (TextView) findViewById(R.id.txtForgotPass);
        btnLogIn = (Button) findViewById(R.id.btnLogIn);
        btnLogIn.setOnClickListener(this);
        txtForgotPass.setOnClickListener(this);
        mServerSyncManager.setOnStringErrorReceived(this);
        mServerSyncManager.setOnStringResultReceived(this);
    }

    @Override
    protected View getMainView() {
        return this.mainClientLoginView;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnLogIn:
                String userName = txtUserName.getText().toString();
                String password = txtPassword.getText().toString();
                String subId = txtSubscriberId.getText().toString();
                callLogin(userName, password, subId);
                break;
            case R.id.txtForgotPass:
                startActivity(new Intent(getApplicationContext(), UserForgotPassActivity.class));
                break;
        }
    }

    private void callLogin(String userName, String password, String subId) {

        boolean cancelFlag = false;
        View focusView = null;
        txtUserName.setError(null);
        txtPassword.setError(null);
        txtSubscriberId.setError(null);
        if (TextUtils.isEmpty(userName)) {
            cancelFlag = true;
            focusView = txtUserName;
            txtUserName.setError(getResources().getString(R.string.str_error_user_name_empty));
        } else if (TextUtils.isEmpty(password)) {
            cancelFlag = true;
            focusView = txtPassword;
            txtPassword.setError(getResources().getString(R.string.str_err_pass_empty));
        } else if (TextUtils.isEmpty(subId)) {
            cancelFlag = true;
            focusView = txtSubscriberId;
            txtSubscriberId.setError(getResources().getString(R.string.str_err_sub_empty));
        }

        if (cancelFlag) {
            focusView.requestFocus();
        } else {
            try {
                int userSubId = Integer.parseInt(subId);
                OneSignalIdHandler signalIdHandler = new OneSignalIdHandler();
                GetUserSubLogin userSubLogin = new GetUserSubLogin(userName, password, subId, signalIdHandler.getUserId());
                Gson gson = new Gson();
                String serializedJsonString = gson.toJson(userSubLogin);
                BaseRequestDTO baseRequestDTO = new BaseRequestDTO();
                baseRequestDTO.setData(serializedJsonString);
                mServerSyncManager.uploadDataToServer(ServerRequestConstants.REQUEST_CLIENT_LOGIN,
                        mSessionManager.getClientLoginUrl(), baseRequestDTO);
            } catch (NumberFormatException e) {
                txtSubscriberId.requestFocus();
                txtSubscriberId.setError(getResources().getString(R.string.str_err_sub_num_invalid));
            }

        }
    }

    @Override
    public void onVolleyErrorReceived(@NonNull VolleyError error, int requestToken) {
        switch (requestToken) {
            case ServerRequestConstants.REQUEST_CLIENT_LOGIN:
                Log.e(TAG, "Error in Client Login" + error.toString());
                break;
        }
    }

    @Override
    public void onDataErrorReceived(ResponseErrorDTO errorDbDTO, int requestToken) {
        switch (requestToken) {
            case ServerRequestConstants.REQUEST_CLIENT_LOGIN:
                Log.e(TAG, "Error in Client Login data" + errorDbDTO.getMessage());
                break;
        }
    }

    @Override
    public void onResultReceived(@NonNull String data, int requestToken) {
        switch (requestToken) {
            case ServerRequestConstants.REQUEST_CLIENT_LOGIN:
                Log.d(TAG, "Success Login " + data);
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
                break;
        }
    }
}
