package com.vibeosys.tradenow.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.vibeosys.tradenow.data.requestdata.BaseRequestDTO;
import com.vibeosys.tradenow.data.requestdata.GetSignalRequest;
import com.vibeosys.tradenow.data.responsedata.ResponseErrorDTO;
import com.vibeosys.tradenow.data.responsedata.ResponseSignalDTO;
import com.vibeosys.tradenow.database.DbRepository;
import com.vibeosys.tradenow.interfaces.BackgroundTaskCallback;
import com.vibeosys.tradenow.utils.SessionManager;

import java.util.List;

/**
 * Created by akshay on 22-06-2016.
 */
public class SignalSyncManager implements ServerSyncManager.OnSuccessResultReceived, ServerSyncManager.OnErrorResultReceived {

    private static final String TAG = SignalSyncManager.class.getSimpleName();
    private DbRepository mDbRepository;
    private SessionManager mSessionManager;
    private Context mContext;
    private ServerSyncManager mSyncManager;

    public SignalSyncManager() {
    }

    public SignalSyncManager(Context context, SessionManager sessionManager, ServerSyncManager syncManager) {
        this.mSessionManager = sessionManager;
        this.mContext = context;
        this.mSyncManager = syncManager;
        mDbRepository = new DbRepository(mContext, mSessionManager);
        mSyncManager.setOnStringErrorReceived(this);
        mSyncManager.setOnStringResultReceived(this);
    }

    public void syncWithServer() {
        GetSignalRequest signalRequest = new GetSignalRequest(mSessionManager.getLastSyncDate());
        Gson gson = new Gson();
        String serializedJsonString = gson.toJson(signalRequest);
        BaseRequestDTO baseRequestDTO = new BaseRequestDTO();
        baseRequestDTO.setData(serializedJsonString);
        mSyncManager.uploadDataToServer(ServerRequestConstants.REQUEST_SIGNAL,
                mSessionManager.getSignalUrl(), baseRequestDTO);
    }

    @Override
    public void onVolleyErrorReceived(@NonNull VolleyError error, int requestToken) {
        switch (requestToken) {
            case ServerRequestConstants.REQUEST_SIGNAL:
                Log.e(TAG, "##Volley Server error " + error.toString());
                break;
        }
    }

    @Override
    public void onDataErrorReceived(ResponseErrorDTO errorDbDTO, int requestToken) {
        switch (requestToken) {
            case ServerRequestConstants.REQUEST_SIGNAL:
                Log.d(TAG, "##Volley Data error " + errorDbDTO.toString());
                break;
        }
    }

    @Override
    public void onResultReceived(@NonNull String data, int requestToken) {
        switch (requestToken) {
            case ServerRequestConstants.REQUEST_SIGNAL:
                List<ResponseSignalDTO> signalDTOList = ResponseSignalDTO.deserializeToArray(data);
                mDbRepository.insertSignal(signalDTOList);
                Log.d(TAG, "##Volley Response" + data);
                break;
        }
    }
}
