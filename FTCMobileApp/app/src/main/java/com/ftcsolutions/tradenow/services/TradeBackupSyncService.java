package com.ftcsolutions.tradenow.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.ftcsolutions.tradenow.utils.NetworkUtils;
import com.ftcsolutions.tradenow.utils.ServerSyncManager;
import com.ftcsolutions.tradenow.utils.SessionManager;
import com.ftcsolutions.tradenow.utils.TradeBackupSyncManager;

/**
 * Created by akshay on 22-06-2016.
 */
public class TradeBackupSyncService extends IntentService {

    private static final String TAG = TradeBackupSyncService.class.getSimpleName();

    public TradeBackupSyncService() {
        super(TradeBackupSyncService.class.getName());
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
        TradeBackupSyncManager mTradeBackupSyncManager = new TradeBackupSyncManager(getApplicationContext(),
                mSessionManager, mServerSyncManager);

        while (true) {
            synchronized (this) {
                try {
                    if (flagStop)
                        break;
                    if (NetworkUtils.isActiveNetworkAvailable(getApplicationContext()))
                        mTradeBackupSyncManager.syncWithServer();

                    Log.d(TAG, "##In trade backup sync service");
                    //TODO: Hardcoded time for now, need to read from properties
                    wait(mSessionManager.getSignalSyncTime() * 1000*60);
                } catch (Exception e) {
                    Log.e(TAG, "##Error occurred in trade backup Sync service " + e.toString());
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
