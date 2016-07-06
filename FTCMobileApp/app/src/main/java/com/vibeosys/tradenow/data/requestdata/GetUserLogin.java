package com.vibeosys.tradenow.data.requestdata;

import com.vibeosys.tradenow.data.BaseDTO;

/**
 * Created by akshay on 24-06-2016.
 */
public class GetUserLogin extends BaseDTO {

    private String username;
    private String pwd;
    private String gcmId;

    public GetUserLogin(String username, String pwd, String gcmId) {
        this.username = username;
        this.pwd = pwd;
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

    public String getGcmId() {
        return gcmId;
    }

    public void setGcmId(String gcmId) {
        this.gcmId = gcmId;
    }
}
