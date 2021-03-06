package com.ftcsolutions.tradenow.utils;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ftcsolutions.tradenow.activities.LoginActivity;
import com.ftcsolutions.tradenow.data.requestdata.BaseRequestDTO;
import com.ftcsolutions.tradenow.data.requestdata.UserRequestDTO;
import com.ftcsolutions.tradenow.data.responsedata.BaseResponseDTO;
import com.ftcsolutions.tradenow.data.responsedata.ResponseErrorDTO;
import com.ftcsolutions.tradenow.database.DbRepository;
import com.ftcsolutions.tradenow.interfaces.BackgroundTaskCallback;

import org.json.JSONObject;

/**
 * Created by akshay on 22-06-2016.
 */
public class ServerSyncManager
        implements BackgroundTaskCallback {

    private SessionManager mSessionManager;
    private Context mContext;
    private DbRepository mDbRepository = null;
    private OnSuccessResultReceived mOnSuccessResultReceived;
    private OnErrorResultReceived mErrorReceived;
    private OnSuccessResultReceived mOnSuccessResultSettingsReceived;
    private String TAG = ServerSyncManager.class.getSimpleName();
    //private OnNotifyUser mNotifyUser;

    public ServerSyncManager() {

    }

    public ServerSyncManager(@NonNull Context context, @NonNull SessionManager sessionManager) {
        mContext = context;
        mSessionManager = sessionManager;
        mDbRepository = new DbRepository(mContext, mSessionManager);
    }

    public void uploadDataToServer(int requestToken, String url, BaseRequestDTO params) {
        /*if (mSessionManager.getUserId() == 0 || mSessionManager.getUserName() == null
                || mSessionManager.getUserName().isEmpty()) {
            Log.e("UserNotAuth", "User is not authenticated before upload");
            return;
        }*/
        String uploadJson = prepareUploadJsonFromData(params);
        Log.i(TAG, "##" + url);
        uploadJsonToServer(uploadJson, url, requestToken);
    }

    public void setOnStringResultReceived(OnSuccessResultReceived stringResultReceived) {
        mOnSuccessResultReceived = stringResultReceived;
        mOnSuccessResultSettingsReceived = stringResultReceived;
    }

    public void setOnStringErrorReceived(OnErrorResultReceived stringErrorReceived) {
        mErrorReceived = stringErrorReceived;
    }

    private String prepareUploadJsonFromData(BaseRequestDTO params) {

        UserRequestDTO userRequestDTO = new UserRequestDTO(mSessionManager.getUserId(),
                mSessionManager.getSubId(), mSessionManager.getUserName(),
                mSessionManager.getUserPassword());
        //get the values from session manager
        params.setUser(userRequestDTO.serializeString());
        String uploadJson = params.serializeString();
        Log.i(TAG, "## request json" + uploadJson);
        return uploadJson;
    }

    private void uploadJsonToServer(String uploadJson, String uploadUrl,
                                    final int requestToken) {
        RequestQueue vollyRequest = Volley.newRequestQueue(mContext);

        JsonObjectRequest uploadRequest = new JsonObjectRequest(Request.Method.POST,
                uploadUrl, uploadJson, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                BaseResponseDTO responseDTO = BaseResponseDTO.deserializeJson(response.toString());
                ResponseErrorDTO errorDTO = ResponseErrorDTO.deserializeJson(responseDTO.getError());
                if (responseDTO == null) {
                    Log.e(TAG, "Error to get the data from server");
                    return;
                }
                if (errorDTO.getErrorCode() == 110) {
                    callToLogOut();
                    return;
                }
                if (errorDTO.getErrorCode() > 0) {
                    if (mErrorReceived != null)
                        mErrorReceived.onDataErrorReceived(errorDTO, requestToken);
                    Log.e("Data Error", "Error to get the data");
                    return;
                }

                if (mOnSuccessResultReceived != null) {
                    mOnSuccessResultReceived.onResultReceived(responseDTO.getData(), requestToken);
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (mErrorReceived != null)
                    mErrorReceived.onVolleyErrorReceived(error, requestToken);
            }
        });
        uploadRequest.setRetryPolicy(new DefaultRetryPolicy(20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        vollyRequest.add(uploadRequest);
    }

    private void callToLogOut() {

        mDbRepository.deleteAllData();
        UserAuth.CleanAuthenticationInfo();
        Intent loginIntent = new Intent(mContext, LoginActivity.class);
        loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        mContext.startActivity(loginIntent);
        //finish();
    }

    @Override
    public void onResultReceived(String downloadedJson) {

    }

    public interface OnSuccessResultReceived {
        void onResultReceived(@NonNull String data, int requestToken);
    }


    public interface OnErrorResultReceived {
        void onVolleyErrorReceived(@NonNull VolleyError error, int requestToken);

        void onDataErrorReceived(ResponseErrorDTO errorDbDTO, int requestToken);
    }

}
