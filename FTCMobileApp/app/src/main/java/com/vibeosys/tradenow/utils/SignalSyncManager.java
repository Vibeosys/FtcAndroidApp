package com.vibeosys.tradenow.utils;

import android.content.Context;

import com.vibeosys.tradenow.database.DbRepository;
import com.vibeosys.tradenow.interfaces.BackgroundTaskCallback;
import com.vibeosys.tradenow.utils.SessionManager;

/**
 * Created by akshay on 22-06-2016.
 */
public class SignalSyncManager {

    private DbRepository mDbRepository;
    private SessionManager mSessionManager;
    private Context mContext;

    public SignalSyncManager() {
    }

    public SignalSyncManager(Context mContext, SessionManager mSessionManager) {
        this.mSessionManager = mSessionManager;
        this.mContext = mContext;
        mDbRepository = new DbRepository(mContext, mSessionManager);
    }
}
