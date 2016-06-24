package com.vibeosys.tradenow.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.vibeosys.tradenow.MainActivity;
import com.vibeosys.tradenow.R;
import com.vibeosys.tradenow.activities.ForgotPassActivity;
import com.vibeosys.tradenow.activities.TermsAndConditionActivity;
import com.vibeosys.tradenow.data.UserDTO;
import com.vibeosys.tradenow.data.requestdata.BaseRequestDTO;
import com.vibeosys.tradenow.data.requestdata.GetUserSubLogin;
import com.vibeosys.tradenow.data.responsedata.ResponseLoginDTO;
import com.vibeosys.tradenow.data.responsedata.ResponseErrorDTO;
import com.vibeosys.tradenow.utils.ServerRequestConstants;
import com.vibeosys.tradenow.utils.ServerSyncManager;
import com.vibeosys.tradenow.utils.UserAuth;

/**
 * Created by akshay on 18-06-2016.
 */
public class ClientUserLoginFragment extends BaseFragment implements View.OnClickListener,
        ServerSyncManager.OnErrorResultReceived, ServerSyncManager.OnSuccessResultReceived {

    private static final String TAG = ClientUserLoginFragment.class.getSimpleName();
    EditText txtUserName, txtPassword, txtSubscriberId;
    TextView txtForgotPass, txtTerms;
    Button btnLogIn;
    View formView, progressView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_login_client, container, false);

        txtUserName = (EditText) view.findViewById(R.id.txtUserName);
        txtPassword = (EditText) view.findViewById(R.id.txtPassword);
        txtForgotPass = (TextView) view.findViewById(R.id.txtForgotPass);
        txtSubscriberId = (EditText) view.findViewById(R.id.txtSubscriberId);
        txtTerms = (TextView) view.findViewById(R.id.txtTerms);
        txtTerms.setText(Html.fromHtml(getResources().getString(R.string.privacy_text_check)));
        btnLogIn = (Button) view.findViewById(R.id.btnLogIn);
        formView = view.findViewById(R.id.formLogin);
        progressView = view.findViewById(R.id.progressBar);
        btnLogIn.setOnClickListener(this);
        txtForgotPass.setOnClickListener(this);
        txtTerms.setOnClickListener(this);
        mServerSyncManager.setOnStringErrorReceived(this);
        mServerSyncManager.setOnStringResultReceived(this);
        return view;
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
                startActivity(new Intent(getActivity().getApplicationContext(), ForgotPassActivity.class));
                break;
            case R.id.txtTerms:
                startActivity(new Intent(getActivity().getApplicationContext(), TermsAndConditionActivity.class));
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
                showProgress(true, formView, progressView);
                int userSubId = Integer.parseInt(subId);
                GetUserSubLogin userSubLogin = new GetUserSubLogin(userName, password, userSubId);
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
                showProgress(false, formView, progressView);
                Log.e(TAG, "Error in Client Login" + error.toString());
                break;
        }
    }

    @Override
    public void onDataErrorReceived(ResponseErrorDTO errorDbDTO, int requestToken) {
        switch (requestToken) {
            case ServerRequestConstants.REQUEST_CLIENT_LOGIN:
                showProgress(false, formView, progressView);
                Log.e(TAG, "Error in Client Login data" + errorDbDTO.getMessage());
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
            case ServerRequestConstants.REQUEST_CLIENT_LOGIN:
                showProgress(false, formView, progressView);
                Log.d(TAG, "Success Login " + data);
                ResponseLoginDTO loginDTO = ResponseLoginDTO.deserializeJson(data);
                UserDTO userDTO = new UserDTO(loginDTO.getUserId(), loginDTO.getFullName(),
                        loginDTO.getUsername(), loginDTO.getPwd(), loginDTO.getEmail(), loginDTO.getSubscriberId());
                UserAuth userAuth = new UserAuth();
                userAuth.saveAuthenticationInfo(userDTO, getContext());
                Intent mainIntent = new Intent(getContext(), MainActivity.class);
                mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(mainIntent);
                getActivity().finish();
                break;
        }
    }
}
