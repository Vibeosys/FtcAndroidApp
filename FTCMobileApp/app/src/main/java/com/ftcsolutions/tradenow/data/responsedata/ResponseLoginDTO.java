package com.ftcsolutions.tradenow.data.responsedata;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.ftcsolutions.tradenow.data.BaseDTO;

/**
 * Created by akshay on 24-06-2016.
 */
public class ResponseLoginDTO extends BaseDTO {

    private static final String TAG = ResponseLoginDTO.class.getSimpleName();
    private long userId;
    private String fullName;
    private String username;
    private String pwd;
    private String email;
    private String subscriberId;

    public ResponseLoginDTO() {
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
    }

    public static ResponseLoginDTO deserializeJson(String serializedString) {
        Gson gson = new Gson();
        ResponseLoginDTO client = null;
        try {
            client = gson.fromJson(serializedString, ResponseLoginDTO.class);
        } catch (JsonParseException e) {
            Log.d(TAG, "Exception in deserialization" + e.toString());
        }
        return client;
    }
}
