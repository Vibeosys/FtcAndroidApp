package com.vibeosys.tradenow.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.vibeosys.tradenow.R;
import com.vibeosys.tradenow.data.requestdata.BaseRequestDTO;
import com.vibeosys.tradenow.data.requestdata.GetProfileRequest;
import com.vibeosys.tradenow.data.responsedata.ResponseErrorDTO;
import com.vibeosys.tradenow.data.responsedata.ResponseGetProfile;
import com.vibeosys.tradenow.utils.ServerRequestConstants;
import com.vibeosys.tradenow.utils.ServerSyncManager;

public class MyProfileActivity extends BaseActivity implements View.OnClickListener,
        ServerSyncManager.OnErrorResultReceived, ServerSyncManager.OnSuccessResultReceived {

    private static final String TAG = MyProfileActivity.class.getSimpleName();
    private EditText mTxtUserId, mTxtUserName, mTxtName, mTxtEmail, mTxtPhNo,
            mTxtSubId, mTxtCompanyname, mTxtPlan;
    private Button mBtnCancel, mBtnUpdate;
    View formView, progressBar;

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
        mServerSyncManager.setOnStringErrorReceived(this);
        mServerSyncManager.setOnStringResultReceived(this);
        getData();
        hideVisibility();
    }

    @Override
    protected View getMainView() {
        return null;
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
                break;
            case R.id.btnCancel:
                hideVisibility();
                break;
        }
    }

    @Override
    public void onVolleyErrorReceived(@NonNull VolleyError error, int requestToken) {
        switch (requestToken) {
            case ServerRequestConstants.REQUEST_GET_PROFILE:
                showProgress(false, formView, progressBar);
                Log.e(TAG, "error in get profile" + error.toString());
                break;
        }

    }

    @Override
    public void onDataErrorReceived(ResponseErrorDTO errorDbDTO, int requestToken) {
        switch (requestToken) {
            case ServerRequestConstants.REQUEST_GET_PROFILE:
                showProgress(false, formView, progressBar);
                Log.e(TAG, "error in get profile" + errorDbDTO.getMessage());
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
