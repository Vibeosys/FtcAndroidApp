package com.vibeosys.tradenow.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.vibeosys.tradenow.R;

public class MyProfileActivity extends BaseActivity implements View.OnClickListener {

    private EditText mTxtName, mTxtEmail, mTxtPhNo, mTxtSubId;
    private Button mBtnCancel, mBtnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        setTitle(getResources().getString(R.string.my_profile));
        mTxtName = (EditText) findViewById(R.id.txtName);
        mTxtEmail = (EditText) findViewById(R.id.txtEmail);
        mTxtPhNo = (EditText) findViewById(R.id.txtPhNo);
        mBtnCancel = (Button) findViewById(R.id.btnCancel);
        mBtnUpdate = (Button) findViewById(R.id.btnUpdate);
        mTxtSubId = (EditText) findViewById(R.id.txtSubscriptionId);
        hideVisibility();

        mTxtName.setText(mSessionManager.getUserFullName());
        mTxtEmail.setText(mSessionManager.getUserEmailId());
        mTxtSubId.setText("" + mSessionManager.getSubId());
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
}
