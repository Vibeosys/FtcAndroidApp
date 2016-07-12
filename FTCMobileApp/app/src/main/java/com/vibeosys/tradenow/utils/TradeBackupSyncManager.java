package com.vibeosys.tradenow.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.vibeosys.tradenow.data.requestdata.BaseRequestDTO;
import com.vibeosys.tradenow.data.requestdata.GetSignalRequest;
import com.vibeosys.tradenow.data.responsedata.ResponseErrorDTO;
import com.vibeosys.tradenow.data.responsedata.ResponseSignalDTO;
import com.vibeosys.tradenow.data.responsedata.ResponseTradeBackUp;
import com.vibeosys.tradenow.database.DbRepository;

import java.util.List;

/**
 * Created by akshay on 22-06-2016.
 */
public class TradeBackupSyncManager implements ServerSyncManager.OnSuccessResultReceived, ServerSyncManager.OnErrorResultReceived {

    private static final String TAG = TradeBackupSyncManager.class.getSimpleName();
    private DbRepository mDbRepository;
    private SessionManager mSessionManager;
    private Context mContext;
    private ServerSyncManager mSyncManager;

    public TradeBackupSyncManager() {
    }

    public TradeBackupSyncManager(Context context, SessionManager sessionManager, ServerSyncManager syncManager) {
        this.mSessionManager = sessionManager;
        this.mContext = context;
        this.mSyncManager = syncManager;
        mDbRepository = new DbRepository(mContext, mSessionManager);
        mSyncManager.setOnStringErrorReceived(this);
        mSyncManager.setOnStringResultReceived(this);
    }

    public void syncWithServer() {
        GetSignalRequest signalRequest = new GetSignalRequest(mSessionManager.getLastBackupSyncDate());
        Gson gson = new Gson();
        String serializedJsonString = gson.toJson(signalRequest);
        BaseRequestDTO baseRequestDTO = new BaseRequestDTO();
        baseRequestDTO.setData(serializedJsonString);
        mSyncManager.uploadDataToServer(ServerRequestConstants.REQUEST_TRADE_BACKUP,
                mSessionManager.getTradeBackupUrl(), baseRequestDTO);
    }

    @Override
    public void onVolleyErrorReceived(@NonNull VolleyError error, int requestToken) {
        switch (requestToken) {
            case ServerRequestConstants.REQUEST_TRADE_BACKUP:
                Log.e(TAG, "##Volley Server error " + error.toString());
                break;
        }
    }

    @Override
    public void onDataErrorReceived(ResponseErrorDTO errorDbDTO, int requestToken) {
        switch (requestToken) {
            case ServerRequestConstants.REQUEST_TRADE_BACKUP:
                Log.d(TAG, "##Volley Data error " + errorDbDTO.toString());
                break;
        }
    }

    @Override
    public void onResultReceived(@NonNull String data, int requestToken) {
        switch (requestToken) {
            case ServerRequestConstants.REQUEST_TRADE_BACKUP:
                List<ResponseTradeBackUp> tradeBackupDTOList = ResponseTradeBackUp.deserializeToArray(data);
                AsyncInsertDb asyncInsertDb = new AsyncInsertDb(tradeBackupDTOList);
                asyncInsertDb.execute();
                Log.d(TAG, "##Volley Response" + data);
                break;
        }
    }

    private class AsyncInsertDb extends AsyncTask<Void, Void, Boolean> {

        List<ResponseTradeBackUp> tradeBackupDTOList;

        AsyncInsertDb(List<ResponseTradeBackUp> tradeBackupDTOList) {
            this.tradeBackupDTOList = tradeBackupDTOList;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            boolean flagQuery = mDbRepository.insertTradeBackUp(tradeBackupDTOList);
            return flagQuery;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if (result) {
                try {
                    ResponseTradeBackUp responseTradeBackupDTO = tradeBackupDTOList.get(tradeBackupDTOList.size() - 1);
                    mSessionManager.setLastBackupSyncDate(responseTradeBackupDTO.getCloseTime());
                } catch (ArrayIndexOutOfBoundsException e) {
                    Log.e(TAG, " Error at trade backup Sync " + e.toString());
                }

            }
        }
    }
}
