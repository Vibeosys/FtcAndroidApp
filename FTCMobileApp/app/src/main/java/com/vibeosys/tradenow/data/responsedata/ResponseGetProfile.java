package com.vibeosys.tradenow.data.responsedata;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.vibeosys.tradenow.data.BaseDTO;

/**
 * Created by akshay on 25-06-2016.
 */
public class ResponseGetProfile extends BaseDTO {

    private static final String TAG = ResponseGetProfile.class.getSimpleName();
    private long userId;
    private String username;
    private String fullName;
    private String email;
    private String phone;
    private String companyName;
    private String plan;
    private String subscriberId;

    public ResponseGetProfile() {
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
    }

    public static ResponseGetProfile deserializeJson(String serializedString) {
        Gson gson = new Gson();
        ResponseGetProfile profile = null;
        try {
            profile = gson.fromJson(serializedString, ResponseGetProfile.class);
        } catch (JsonParseException e) {
            Log.d(TAG, "Exception in deserialization" + e.toString());
        }
        return profile;
    }
}
