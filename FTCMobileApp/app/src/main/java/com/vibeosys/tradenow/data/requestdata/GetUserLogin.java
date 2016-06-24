package com.vibeosys.tradenow.data.requestdata;

import com.vibeosys.tradenow.data.BaseDTO;

/**
 * Created by akshay on 24-06-2016.
 */
public class GetUserLogin extends BaseDTO {

    private String username;
    private String pwd;

    public GetUserLogin(String username, String pwd) {
        this.username = username;
        this.pwd = pwd;
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
}
