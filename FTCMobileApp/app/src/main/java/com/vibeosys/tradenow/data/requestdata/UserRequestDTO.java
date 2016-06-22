package com.vibeosys.tradenow.data.requestdata;

import com.vibeosys.tradenow.data.BaseDTO;

/**
 * Created by akshay on 22-06-2016.
 */
public class UserRequestDTO extends BaseDTO {

    private long userId;
    private long subscriberId;
    private String userName;
    private String pwd;

    public UserRequestDTO(long userId, long subscriberId, String userName, String pwd) {
        this.userId = userId;
        this.subscriberId = subscriberId;
        this.userName = userName;
        this.pwd = pwd;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(long subscriberId) {
        this.subscriberId = subscriberId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
