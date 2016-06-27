package com.vibeosys.tradenow.utils;

import android.content.Context;
import android.support.annotation.NonNull;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.vibeosys.tradenow.data.requestdata.BaseRequestDTO;
import com.vibeosys.tradenow.data.requestdata.GetSignalRequest;
import com.vibeosys.tradenow.data.responsedata.ResponseErrorDTO;
import com.vibeosys.tradenow.database.DbRepository;

/**
 * Created by akshay on 27-06-2016.
 */
public class PagesSyncManager implements ServerSyncManager.OnSuccessResultReceived, ServerSyncManager.OnErrorResultReceived {

    private static final String TAG = PagesSyncManager.class.getSimpleName();
    private DbRepository mDbRepository;
    private SessionManager mSessionManager;
    private Context mContext;
    private ServerSyncManager mSyncManager;

    public PagesSyncManager() {
    }

    public PagesSyncManager(Context context, SessionManager sessionManager, ServerSyncManager syncManager) {
        this.mSessionManager = sessionManager;
        this.mContext = context;
        this.mSyncManager = syncManager;
        mDbRepository = new DbRepository(mContext, mSessionManager);
        mSyncManager.setOnStringErrorReceived(this);
        mSyncManager.setOnStringResultReceived(this);
    }

    public void syncWithServer() {
        //Server call
    }

    @Override
    public void onVolleyErrorReceived(@NonNull VolleyError error, int requestToken) {

    }

    @Override
    public void onDataErrorReceived(ResponseErrorDTO errorDbDTO, int requestToken) {

    }

    @Override
    public void onResultReceived(@NonNull String data, int requestToken) {

    }
}
