package com.ftcsolutions.tradenow.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.ftcsolutions.tradenow.database.DbRepository;
import com.ftcsolutions.tradenow.utils.NetworkUtils;
import com.ftcsolutions.tradenow.utils.ServerSyncManager;
import com.ftcsolutions.tradenow.utils.SessionManager;
import com.ftcsolutions.tradenow.utils.SignalSyncManager;

/**
 * Created by akshay on 15-06-2016.
 */
public abstract class BaseActivity extends AppCompatActivity {

    private static final String TAG = BaseActivity.class.getSimpleName();
    protected ServerSyncManager mServerSyncManager = null;
    protected SignalSyncManager mSignalSyncManager = null;
    protected DbRepository mDbRepository = null;
    protected static SessionManager mSessionManager = null;
    protected View mainView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSessionManager = SessionManager.getInstance(getApplicationContext());
        Log.d("##", "##" + mSessionManager.getDatabaseDeviceFullPath());
        mServerSyncManager = new ServerSyncManager(getApplicationContext(), mSessionManager);
        mDbRepository = new DbRepository(getApplicationContext(), mSessionManager);
        mSignalSyncManager = new SignalSyncManager(getApplicationContext(),
                mSessionManager, mServerSyncManager);
        mDbRepository.getDatabaseStructure();
    }

    protected void customAlterDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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

    protected void createNetworkAlertDialog(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // whatever...
                        dialog.dismiss();
                        startActivityForResult(new Intent(Settings.ACTION_SETTINGS), 0);
                    }
                }).create().show();
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

    @Override
    protected void onResume() {
        super.onResume();
        View view = new View(getApplicationContext());
        if (!NetworkUtils.isActiveNetworkAvailable(getApplicationContext())) {
            try {
                view = getMainView();
                Snackbar snackbar = Snackbar
                        .make(view, "No internet connection!", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Setting", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivityForResult(new Intent(Settings.ACTION_SETTINGS), 0);
                            }
                        });
// Changing message text color
                snackbar.setActionTextColor(Color.RED);
                snackbar.show();
            } catch (NullPointerException e) {
                Log.e(TAG, " Error at base activity null view" + e.toString());
            }

        }
    }

    protected boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    protected abstract View getMainView() throws NullPointerException;
}
