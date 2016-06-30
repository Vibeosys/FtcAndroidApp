package com.vibeosys.tradenow.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.android.volley.VolleyError;
import com.vibeosys.tradenow.data.TableJsonCollectionDTO;
import com.vibeosys.tradenow.data.requestdata.BaseRequestDTO;
import com.vibeosys.tradenow.data.responsedata.ResponseErrorDTO;
import com.vibeosys.tradenow.data.responsedata.ResponseSyncDataDTO;
import com.vibeosys.tradenow.database.DbRepository;
import com.vibeosys.tradenow.database.SqlContract;

import java.util.ArrayList;
import java.util.Hashtable;

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
        BaseRequestDTO baseRequestDTO = new BaseRequestDTO();
        mSyncManager.uploadDataToServer(ServerRequestConstants.REQUEST_SYNC_PAGES,
                mSessionManager.getSyncPageUrl(), baseRequestDTO);
    }

    @Override
    public void onVolleyErrorReceived(@NonNull VolleyError error, int requestToken) {
        switch (requestToken) {
            case ServerRequestConstants.REQUEST_SYNC_PAGES:
                break;
            default:
                break;
        }

    }

    @Override
    public void onDataErrorReceived(ResponseErrorDTO errorDbDTO, int requestToken) {
        switch (requestToken) {
            case ServerRequestConstants.REQUEST_SYNC_PAGES:
                break;
            default:
                break;
        }
    }

    @Override
    public void onResultReceived(@NonNull String data, int requestToken) {
        switch (requestToken) {
            case ServerRequestConstants.REQUEST_SYNC_PAGES:
                Log.d(TAG, "## pageSync" + data);
                InsertUpdateDb updateDb = new InsertUpdateDb();
                updateDb.execute(data);
                break;
            default:
                break;
        }
    }

    private class InsertUpdateDb extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            ArrayList<ResponseSyncDataDTO> syncDataDTOs = ResponseSyncDataDTO.deserializeToArray(params[0]);
            Hashtable<String, TableJsonCollectionDTO> theTableData = new Hashtable<>();

            for (ResponseSyncDataDTO tableData : syncDataDTOs) {
                String theTableName = tableData.getTableName();
                String theTableValue = tableData.getJsonData();

                TableJsonCollectionDTO tableJsonCollectionDTO;
                if (!theTableData.containsKey(theTableName)) {
                    tableJsonCollectionDTO = new TableJsonCollectionDTO();
                    theTableData.put(theTableName, tableJsonCollectionDTO);
                }

                if (tableData.getTableOperation().compareToIgnoreCase("insert") == 0)
                    theTableData.get(theTableName).getInsertJsonList().add(theTableValue);

                if (tableData.getTableOperation().compareToIgnoreCase("update") == 0)
                    theTableData.get(theTableName).getUpdateJsonList().add(theTableValue);

                if (tableData.getTableOperation().compareToIgnoreCase("delete") == 0)
                    theTableData.get(theTableName).getDeleteJsonList().add(theTableValue);
            }
            DbOperations dbOperations = new DbOperations(mDbRepository);

            if (theTableData.containsKey(SqlContract.SqlPage.TABLE_NAME)) {
                TableJsonCollectionDTO tableValue = theTableData.get(SqlContract.SqlPage.TABLE_NAME);
                ArrayList<String> jsonInsertList = tableValue.getInsertJsonList();
                ArrayList<String> jsonUpdateList = tableValue.getUpdateJsonList();
                dbOperations.addOrUpdatePage(jsonInsertList, jsonUpdateList);
                Log.d("TableDataDTO", "##" + SqlContract.SqlPage.TABLE_NAME);
                Log.d("TableDataDTO", "## Insert " + jsonInsertList + "\n update " + jsonUpdateList);
            }
            if (theTableData.containsKey(SqlContract.SqlWidget.TABLE_NAME)) {
                TableJsonCollectionDTO tableValue = theTableData.get(SqlContract.SqlWidget.TABLE_NAME);
                ArrayList<String> jsonInsertList = tableValue.getInsertJsonList();
                ArrayList<String> jsonUpdateList = tableValue.getUpdateJsonList();
                dbOperations.addOrUpdateWidgets(jsonInsertList, jsonUpdateList);
                Log.d("TableDataDTO", "##" + SqlContract.SqlWidget.TABLE_NAME);
                Log.d("TableDataDTO", "## Insert " + jsonInsertList + "\n update " + jsonUpdateList);
            }
            Log.d("TableDataDTO", "## Table Data " + theTableData);
            return null;
        }
    }
}
