package com.ftcsolutions.tradenow.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.ftcsolutions.tradenow.utils.NetworkUtils;
import com.ftcsolutions.tradenow.utils.ServerSyncManager;
import com.ftcsolutions.tradenow.utils.SessionManager;
import com.ftcsolutions.tradenow.utils.SignalSyncManager;

/**
 * Created by akshay on 22-06-2016.
 */
public class SignalSyncService extends IntentService {

    private static final String TAG = SignalSyncService.class.getSimpleName();

    public SignalSyncService() {
        super(SignalSyncService.class.getName());
    }

    boolean flagStop = false;

    @Override
    public void onCreate() {
        super.onCreate();
        flagStop = false;
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
                    if (flagStop)
                        break;
                    if (NetworkUtils.isActiveNetworkAvailable(getApplicationContext()))
                        mSignalSyncManager.syncWithServer();

                    Log.d(TAG, "##In Signal sync service");
                    //TODO: Hardcoded time for now, need to read from properties
                    wait(mSessionManager.getSignalSyncTime() * 1000*60);
                } catch (Exception e) {
                    Log.e(TAG, "##Error occurred in Signal Sync service " + e.toString());
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        flagStop = true;
    }
}
