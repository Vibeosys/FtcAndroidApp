package com.vibeosys.tradenow.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.vibeosys.tradenow.R;
import com.vibeosys.tradenow.data.requestdata.BaseRequestDTO;
import com.vibeosys.tradenow.data.requestdata.GetProfileRequest;
import com.vibeosys.tradenow.data.requestdata.RequestUpdateProfile;
import com.vibeosys.tradenow.data.responsedata.ResponseErrorDTO;
import com.vibeosys.tradenow.data.responsedata.ResponseGetProfile;
import com.vibeosys.tradenow.utils.NetworkUtils;
import com.vibeosys.tradenow.utils.ServerRequestConstants;
import com.vibeosys.tradenow.utils.ServerSyncManager;
import com.vibeosys.tradenow.utils.Validator;

public class MyProfileActivity extends BaseActivity implements View.OnClickListener,
        ServerSyncManager.OnErrorResultReceived, ServerSyncManager.OnSuccessResultReceived {

    private static final String TAG = MyProfileActivity.class.getSimpleName();
    private EditText mTxtUserId, mTxtUserName, mTxtName, mTxtEmail, mTxtPhNo,
            mTxtSubId, mTxtCompanyname, mTxtPlan;
    private Button mBtnCancel, mBtnUpdate;
    View formView, progressBar;
    Validator validator = new Validator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        setTitle(getResources().getString(R.string.my_profile));
        formView = findViewById(R.id.profileView);
        progressBar = findViewById(R.id.progressBar);
        mTxtUserId = (EditText) findViewById(R.id.txtUserId);
        mTxtUserName = (EditText) findViewById(R.id.txtUserName);
        mTxtName = (EditText) findViewById(R.id.txtName);
        mTxtEmail = (EditText) findViewById(R.id.txtEmail);
        mTxtPhNo = (EditText) findViewById(R.id.txtPhNo);
        mBtnCancel = (Button) findViewById(R.id.btnCancel);
        mBtnUpdate = (Button) findViewById(R.id.btnUpdate);
        mTxtCompanyname = (EditText) findViewById(R.id.txtAgencyName);
        mTxtPlan = (EditText) findViewById(R.id.txtPlanName);
        mTxtSubId = (EditText) findViewById(R.id.txtSubscriptionId);
        mBtnCancel.setOnClickListener(this);
        mBtnUpdate.setOnClickListener(this);
        mServerSyncManager.setOnStringErrorReceived(this);
        mServerSyncManager.setOnStringResultReceived(this);
        if (NetworkUtils.isActiveNetworkAvailable(getApplicationContext())) {
            getData();
        } else {
            createNetworkAlertDialog(getResources().getString(R.string.str_net_err),
                    getResources().getString(R.string.str_err_net_msg));
        }
        hideVisibility();
    }

    @Override
    protected View getMainView() {
        return formView;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_update, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_edit:
                showVisibility();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void hideVisibility() {
        mTxtName.setEnabled(false);
        mTxtEmail.setEnabled(false);
        mTxtPhNo.setEnabled(false);
        mBtnCancel.setVisibility(View.GONE);
        mBtnUpdate.setVisibility(View.GONE);
    }

    private void showVisibility() {
        mTxtName.setEnabled(true);
        mTxtEmail.setEnabled(true);
        mTxtPhNo.setEnabled(true);
        mBtnCancel.setVisibility(View.VISIBLE);
        mBtnUpdate.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnUpdate:
                if (NetworkUtils.isActiveNetworkAvailable(getApplicationContext())) {
                    String strFullName = mTxtName.getText().toString();
                    String strEmail = mTxtEmail.getText().toString();
                    String phone = mTxtPhNo.getText().toString();

                    callToUpdate(strFullName, strEmail, phone);
                } else {
                    createNetworkAlertDialog(getResources().getString(R.string.str_net_err),
                            getResources().getString(R.string.str_err_net_msg));
                }
                break;
            case R.id.btnCancel:
                hideVisibility();
                break;
        }
    }

    private void callToUpdate(String strFullName, String strEmail, String phone) {

        boolean cancelFlag = false;
        View focusView = null;
        mTxtName.setError(null);
        mTxtEmail.setError(null);
        mTxtPhNo.setError(null);

        if (TextUtils.isEmpty(strFullName)) {
            cancelFlag = true;
            focusView = mTxtName;
            mTxtName.setError(getResources().getString(R.string.str_err_name_empty));
        } else if (TextUtils.isEmpty(strEmail)) {
            cancelFlag = true;
            focusView = mTxtEmail;
            mTxtEmail.setError(getResources().getString(R.string.str_err_email_empty));
        } else if (!validator.isValidMail(strEmail)) {
            cancelFlag = true;
            focusView = mTxtEmail;
            mTxtEmail.setError(getResources().getString(R.string.str_err_email_validate));
        } else if (TextUtils.isEmpty(phone)) {
            cancelFlag = true;
            focusView = mTxtPhNo;
            mTxtPhNo.setError(getResources().getString(R.string.str_err_phone_empty));
        } else if (!validator.isValidPhone(phone)) {
            cancelFlag = true;
            focusView = mTxtPhNo;
            mTxtPhNo.setError(getResources().getString(R.string.str_err_phone_validate));
        }

        if (cancelFlag) {
            focusView.requestFocus();
        } else {
            showProgress(true, formView, progressBar);
            RequestUpdateProfile updateProfile = new RequestUpdateProfile(strFullName, strEmail, phone);
            Gson gson = new Gson();
            String serializedJsonString = gson.toJson(updateProfile);
            BaseRequestDTO baseRequestDTO = new BaseRequestDTO();
            baseRequestDTO.setData(serializedJsonString);
            mServerSyncManager.uploadDataToServer(ServerRequestConstants.REQUEST_UPDATE_PROFILE,
                    mSessionManager.getUpdateProfileUrl(), baseRequestDTO);
        }
    }

    @Override
    public void onVolleyErrorReceived(@NonNull VolleyError error, int requestToken) {
        switch (requestToken) {
            case ServerRequestConstants.REQUEST_GET_PROFILE:
                showProgress(false, formView, progressBar);
                Log.e(TAG, "error in get profile" + error.toString());
                break;
            case ServerRequestConstants.REQUEST_UPDATE_PROFILE:
                showProgress(false, formView, progressBar);
                Log.e(TAG, "error in update profile" + error.toString());
                break;
            default:
                break;
        }

    }

    @Override
    public void onDataErrorReceived(ResponseErrorDTO errorDbDTO, int requestToken) {
        switch (requestToken) {
            case ServerRequestConstants.REQUEST_GET_PROFILE:
                showProgress(false, formView, progressBar);
                Log.e(TAG, "error in get profile" + errorDbDTO.getMessage());
                Toast.makeText(getApplicationContext(), errorDbDTO.getMessage(),
                        Toast.LENGTH_LONG).show();
                break;
            case ServerRequestConstants.REQUEST_UPDATE_PROFILE:
                showProgress(false, formView, progressBar);
                Log.e(TAG, "error in get profile" + errorDbDTO.getMessage());
                Toast.makeText(getApplicationContext(), errorDbDTO.getMessage(),
                        Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
    }

    @Override
    public void onResultReceived(@NonNull String data, int requestToken) {
        switch (requestToken) {
            case ServerRequestConstants.REQUEST_GET_PROFILE:
                showProgress(false, formView, progressBar);
                Log.e(TAG, "data get successfully" + data);
                ResponseGetProfile profileData = ResponseGetProfile.deserializeJson(data);
                setData(profileData);
                break;
            case ServerRequestConstants.REQUEST_UPDATE_PROFILE:
                showProgress(false, formView, progressBar);
                Toast.makeText(getApplicationContext(), "Profile updated successfully.",
                        Toast.LENGTH_LONG).show();
                hideVisibility();
                break;
            default:
                break;
        }
    }

    public void getData() {
        showProgress(true, formView, progressBar);
        GetProfileRequest getProfileRequest = new GetProfileRequest(mSessionManager.getUserId());
        Gson gson = new Gson();
        String serializedJsonString = gson.toJson(getProfileRequest);
        BaseRequestDTO baseRequestDTO = new BaseRequestDTO();
        baseRequestDTO.setData(serializedJsonString);
        mServerSyncManager.uploadDataToServer(ServerRequestConstants.REQUEST_GET_PROFILE,
                mSessionManager.getProfileUrl(), baseRequestDTO);
    }

    public void setData(ResponseGetProfile data) {
        mTxtUserId.setText("" + data.getUserId());
        mTxtUserName.setText(data.getUsername());
        mTxtName.setText(data.getFullName());
        mTxtEmail.setText(data.getEmail());
        mTxtPhNo.setText(data.getPhone());
        mTxtCompanyname.setText(data.getCompanyName());
        mTxtPlan.setText(data.getPlan());
        mTxtSubId.setText("" + data.getSubscriberId());
    }
}
