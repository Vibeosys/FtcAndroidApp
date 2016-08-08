package com.vibeosys.tradenow.data.requestdata;

import com.vibeosys.tradenow.data.BaseDTO;

/**
 * Created by akshay on 23-06-2016.
 */
public class GetUserSubLogin extends BaseDTO {

    private String username;
    private String pwd;
    private String subscriberId;
    private String gcmId;

    public GetUserSubLogin(String username, String pwd, String subscriberId, String gcmId) {
        this.username = username;
        this.pwd = pwd;
        this.subscriberId = subscriberId;
        this.gcmId = gcmId;
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

    public String getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
    }
}
