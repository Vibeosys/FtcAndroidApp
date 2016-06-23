package com.vibeosys.tradenow.data.requestdata;

/**
 * Created by akshay on 23-06-2016.
 */
public class GetUserSubLogin {

    private String username;
    private String pwd;
    private int subscriberId;

    public GetUserSubLogin(String username, String pwd, int subscriberId) {
        this.username = username;
        this.pwd = pwd;
        this.subscriberId = subscriberId;
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

    public int getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(int subscriberId) {
        this.subscriberId = subscriberId;
    }
}
