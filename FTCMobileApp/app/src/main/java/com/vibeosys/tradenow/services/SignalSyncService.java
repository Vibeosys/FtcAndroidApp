package com.vibeosys.tradenow.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.vibeosys.tradenow.utils.AppConstants;
import com.vibeosys.tradenow.utils.NetworkUtils;
import com.vibeosys.tradenow.utils.ServerSyncManager;
import com.vibeosys.tradenow.utils.SessionManager;
import com.vibeosys.tradenow.utils.SignalSyncManager;

/**
 * Created by akshay on 22-06-2016.
 */
public class SignalSyncService extends IntentService {

    public SignalSyncService() {
        super(SignalSyncService.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        SessionManager mSessionManager = SessionManager.getInstance(getApplicationContext());
        ServerSyncManager mServerSyncManager = new ServerSyncManager(getApplicationContext(),
                mSessionManager);
        SignalSyncManager mSignalSyncManager = new SignalSyncManager(getApplicationContext(),
                mSessionManager, mServerSyncManager);

        while (true) {
            synchronized (this) {
                try {
                    if (NetworkUtils.isActiveNetworkAvailable(getApplicationContext()))
                        mSignalSyncManager.syncWithServer();

                    Log.d("SyncService", "##In service");
                    //TODO: Hardcoded time for now, need to read from properties
                    wait(AppConstants.SERVICE_TIME_OUT * 1000);
                } catch (Exception e) {
                    Log.e("SyncService", "##Error occurred in background service " + e.toString());
                }
            }
        }
    }
}
