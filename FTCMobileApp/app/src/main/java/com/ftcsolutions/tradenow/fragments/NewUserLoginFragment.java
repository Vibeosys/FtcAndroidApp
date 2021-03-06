package com.ftcsolutions.tradenow.fragments;

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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.ftcsolutions.tradenow.MainActivity;
import com.ftcsolutions.tradenow.R;
import com.ftcsolutions.tradenow.activities.UserForgotPassActivity;
import com.ftcsolutions.tradenow.activities.RegisterActivity;
import com.ftcsolutions.tradenow.activities.TermsAndConditionActivity;
import com.ftcsolutions.tradenow.data.UserDTO;
import com.ftcsolutions.tradenow.data.requestdata.BaseRequestDTO;
import com.ftcsolutions.tradenow.data.requestdata.GetUserLogin;
import com.ftcsolutions.tradenow.data.responsedata.ResponseErrorDTO;
import com.ftcsolutions.tradenow.data.responsedata.ResponseGetPages;
import com.ftcsolutions.tradenow.data.responsedata.ResponseLoginDTO;
import com.ftcsolutions.tradenow.data.responsedata.ResponsePageData;
import com.ftcsolutions.tradenow.data.responsedata.ResponsePageType;
import com.ftcsolutions.tradenow.data.responsedata.ResponseWidgetData;
import com.ftcsolutions.tradenow.utils.OneSignalIdHandler;
import com.ftcsolutions.tradenow.utils.ServerRequestConstants;
import com.ftcsolutions.tradenow.utils.ServerSyncManager;
import com.ftcsolutions.tradenow.utils.UserAuth;

import java.util.ArrayList;

/**
 * Created by akshay on 18-06-2016.
 */
public class NewUserLoginFragment extends BaseFragment implements View.OnClickListener,
        ServerSyncManager.OnErrorResultReceived, ServerSyncManager.OnSuccessResultReceived {
    private static final String TAG = NewUserLoginFragment.class.getSimpleName();
    private EditText txtUserName, txtPassword;
    private TextView txtRegister, txtForgotPass, txtTerms, txtError;
    private Button btnLogIn;
    private View formView, progressView;
    private CheckBox chkPrivacy;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_login, container, false);
        //setTitle(getResources().getString(R.string.str_login_activity));
        txtUserName = (EditText) view.findViewById(R.id.txtUserName);
        txtPassword = (EditText) view.findViewById(R.id.txtPassword);
        txtRegister = (TextView) view.findViewById(R.id.txtRegister);
        txtForgotPass = (TextView) view.findViewById(R.id.txtForgotPass);
        txtTerms = (TextView) view.findViewById(R.id.txtTerms);
        txtTerms.setText(Html.fromHtml(getResources().getString(R.string.privacy_text_check)));
        btnLogIn = (Button) view.findViewById(R.id.btnLogIn);
        formView = view.findViewById(R.id.formLogin);
        progressView = view.findViewById(R.id.progressBar);
        chkPrivacy = (CheckBox) view.findViewById(R.id.privacyCheck);
        txtError = (TextView) view.findViewById(R.id.txtError);
        btnLogIn.setOnClickListener(this);
        txtRegister.setOnClickListener(this);
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
                boolean checkPrivacy = chkPrivacy.isChecked();
                callLogin(userName, password, checkPrivacy);
                break;
            case R.id.txtRegister:
                startActivity(new Intent(getActivity().getApplicationContext(), RegisterActivity.class));
                break;
            case R.id.txtForgotPass:
                startActivity(new Intent(getActivity().getApplicationContext(), UserForgotPassActivity.class));
                break;
            case R.id.txtTerms:
                startActivity(new Intent(getActivity().getApplicationContext(), TermsAndConditionActivity.class));
                break;
        }
    }

    private void callLogin(String userName, String password, boolean checkPrivacy) {

        boolean cancelFlag = false;
        View focusView = null;
        txtUserName.setError(null);
        txtPassword.setError(null);
        txtError.setVisibility(View.GONE);

        if (TextUtils.isEmpty(userName)) {
            cancelFlag = true;
            focusView = txtUserName;
            txtUserName.setError(getResources().getString(R.string.str_error_user_name_empty));
        } else if (TextUtils.isEmpty(password)) {
            cancelFlag = true;
            focusView = txtPassword;
            txtPassword.setError(getResources().getString(R.string.str_err_pass_empty));
        } else if (!checkPrivacy) {
            cancelFlag = true;
            focusView = chkPrivacy;
            txtError.setVisibility(View.VISIBLE);
        }

        if (cancelFlag) {
            focusView.requestFocus();
        } else {
            txtError.setVisibility(View.GONE);
            showProgress(true, formView, progressView);
            OneSignalIdHandler signalIdHandler = new OneSignalIdHandler();
            GetUserLogin userLogin = new GetUserLogin(userName, password, signalIdHandler.getUserId());
            Gson gson = new Gson();
            String serializedJsonString = gson.toJson(userLogin);
            BaseRequestDTO baseRequestDTO = new BaseRequestDTO();
            baseRequestDTO.setData(serializedJsonString);
            mServerSyncManager.uploadDataToServer(ServerRequestConstants.REQUEST_USER_LOGIN,
                    mSessionManager.getUserLoginUrl(), baseRequestDTO);

        }
    }

    @Override
    public void onVolleyErrorReceived(@NonNull VolleyError error, int requestToken) {
        switch (requestToken) {
            case ServerRequestConstants.REQUEST_USER_LOGIN:
                showProgress(false, formView, progressView);
                customAlterDialog(getResources().getString(R.string.str_err_server_err),
                        getResources().getString(R.string.str_err_server_msg));
                Log.e(TAG, "Error in User Login data" + error.getMessage());
                break;
            case ServerRequestConstants.REQUEST_GET_PAGES:
                showProgress(false, formView, progressView);
                break;
            default:
                showProgress(false, formView, progressView);
                break;
        }
    }

    @Override
    public void onDataErrorReceived(ResponseErrorDTO errorDbDTO, int requestToken) {
        switch (requestToken) {
            case ServerRequestConstants.REQUEST_USER_LOGIN:
                showProgress(false, formView, progressView);
                Log.e(TAG, "Error in User Login data" + errorDbDTO.getMessage());
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
            case ServerRequestConstants.REQUEST_GET_PAGES:
                showProgress(false, formView, progressView);
                Intent mainIntent = new Intent(getContext(), MainActivity.class);
                mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(mainIntent);
                getActivity().finish();
                break;
            default:
                showProgress(false, formView, progressView);
                break;
        }
    }

    @Override
    public void onResultReceived(@NonNull String data, int requestToken) {
        switch (requestToken) {
            case ServerRequestConstants.REQUEST_USER_LOGIN:
                showProgress(false, formView, progressView);
                Log.d(TAG, "Success Login " + data);
                ResponseLoginDTO loginDTO = ResponseLoginDTO.deserializeJson(data);
                UserDTO userDTO = new UserDTO(loginDTO.getUserId(), loginDTO.getFullName(),
                        loginDTO.getUsername(), loginDTO.getPwd(), loginDTO.getEmail(), loginDTO.getSubscriberId());
                UserAuth userAuth = new UserAuth();
                userAuth.saveAuthenticationInfo(userDTO, getContext());
                callToPages();
                break;
            case ServerRequestConstants.REQUEST_GET_PAGES:
                showProgress(false, formView, progressView);
                fillData(data);
                Intent mainIntent = new Intent(getContext(), MainActivity.class);
                mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(mainIntent);
                getActivity().finish();
                break;
            default:
                showProgress(false, formView, progressView);
                break;
        }
    }

    private void fillData(String data) {
        ResponseGetPages getPagesResponse = ResponseGetPages.deserializeJson(data);
        Log.d(TAG, "##" + getPagesResponse.toString());
        ArrayList<ResponsePageType> pageTypes = ResponsePageType.deserializeToArray(getPagesResponse.getPageType());
        mDbRepository.insertPageTypes(pageTypes);
        ArrayList<ResponsePageData> mobilePages = ResponsePageData.deserializeToArray(getPagesResponse.getPages());
        mDbRepository.insertPages(mobilePages);
        ArrayList<ResponseWidgetData> widgetDatas = ResponseWidgetData.deserializeToArray(getPagesResponse.getWidgets());
        mDbRepository.insertWidgets(widgetDatas);
    }

    private void callToPages() {
        showProgress(true, formView, progressView);
        BaseRequestDTO baseRequestDTO = new BaseRequestDTO();
        mServerSyncManager.uploadDataToServer(ServerRequestConstants.REQUEST_GET_PAGES,
                mSessionManager.getPagesUrl(), baseRequestDTO);
    }
}
