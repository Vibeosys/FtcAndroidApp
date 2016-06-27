package com.vibeosys.tradenow.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.vibeosys.tradenow.database.DbRepository;
import com.vibeosys.tradenow.utils.ServerSyncManager;
import com.vibeosys.tradenow.utils.SessionManager;
import com.vibeosys.tradenow.utils.SignalSyncManager;

/**
 * Created by akshay on 18-06-2016.
 */
public class BaseFragment extends Fragment {
    protected ServerSyncManager mServerSyncManager = null;
    protected SignalSyncManager mSignalSyncManager = null;
    protected DbRepository mDbRepository = null;
    protected static SessionManager mSessionManager = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSessionManager = SessionManager.getInstance(getContext());
        Log.d("##", "##" + mSessionManager.getDatabaseDeviceFullPath());
        mServerSyncManager = new ServerSyncManager(getContext(), mSessionManager);
        mDbRepository = new DbRepository(getContext(), mSessionManager);
        mSignalSyncManager = new SignalSyncManager(getContext(),
                mSessionManager, mServerSyncManager);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    protected void showProgress(final boolean show, final View hideFormView, final View showProgressView) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            if (hideFormView != null) {
                hideFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                hideFormView.animate().setDuration(shortAnimTime).alpha(
                        show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        hideFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                    }
                });
            }
            if (showProgressView != null) {
                showProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                showProgressView.animate().setDuration(shortAnimTime).alpha(
                        show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        showProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                    }
                });
            }
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            showProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            hideFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    protected void customAlterDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("" + title);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }
}
