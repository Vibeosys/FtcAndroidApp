package com.vibeosys.tradenow.data.responsedata;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.vibeosys.tradenow.data.BaseDTO;

import java.util.ArrayList;

/**
 * Created by akshay on 30-06-2016.
 */
public class ResponseSyncDataDTO extends BaseDTO {

    private int syncId;
    private String tableName;
    private String tableOperation;
    private String jsonData;

    public ResponseSyncDataDTO() {
    }

    public int getSyncId() {
        return syncId;
    }

    public void setSyncId(int syncId) {
        this.syncId = syncId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableOperation() {
        return tableOperation;
    }

    public void setTableOperation(String tableOperation) {
        this.tableOperation = tableOperation;
    }

    public String getJsonData() {
        return jsonData;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }

    public static ArrayList<ResponseSyncDataDTO> deserializeToArray(String serializedString) {
        Gson gson = new Gson();
        ArrayList<ResponseSyncDataDTO> syncDataDTOs = null;
        try {
            ResponseSyncDataDTO[] deserializeObject = gson.fromJson(serializedString, ResponseSyncDataDTO[].class);
            syncDataDTOs = new ArrayList<>();
            for (ResponseSyncDataDTO syncDTO : deserializeObject) {
                syncDataDTOs.add(syncDTO);
            }
        } catch (JsonSyntaxException e) {
            Log.e("deserialize", "Response ResponseSyncDataDTO error" + e.toString());
        }


        return syncDataDTOs;
    }
}
