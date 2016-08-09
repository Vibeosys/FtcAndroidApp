package com.vibeosys.tradenow.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.vibeosys.tradenow.utils.AppConstants;
import com.vibeosys.tradenow.utils.NetworkUtils;
import com.vibeosys.tradenow.utils.PagesSyncManager;
import com.vibeosys.tradenow.utils.ServerSyncManager;
import com.vibeosys.tradenow.utils.SessionManager;
import com.vibeosys.tradenow.utils.SignalSyncManager;

/**
 * Created by akshay on 27-06-2016.
 */
public class PageSyncService extends IntentService {

    private static final String TAG = PageSyncService.class.getSimpleName();

    public PageSyncService() {
        super(PageSyncService.class.getName());
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
        PagesSyncManager mPagesSyncManager = new PagesSyncManager(getApplicationContext(),
                mSessionManager, mServerSyncManager);

        while (true) {
            synchronized (this) {
                try {
                    if (flagStop)
                        break;
                    if (NetworkUtils.isActiveNetworkAvailable(getApplicationContext()))
                        mPagesSyncManager.syncWithServer();

                    Log.d(TAG, "##In page service");
                    //TODO: Hardcoded time for now, need to read from properties
                    wait(mSessionManager.getPageSyncTime() * 1000 * 60);
                } catch (Exception e) {
                    Log.e(TAG, "##Error occurred in page service " + e.toString());
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
