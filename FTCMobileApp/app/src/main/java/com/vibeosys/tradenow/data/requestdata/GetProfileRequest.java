package com.vibeosys.tradenow.data.requestdata;

/**
 * Created by akshay on 25-06-2016.
 */
public class GetProfileRequest {
    private long userId;

    public GetProfileRequest(long userId) {
        this.userId = userId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
