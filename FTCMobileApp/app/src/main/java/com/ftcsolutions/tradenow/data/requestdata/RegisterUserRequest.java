package com.ftcsolutions.tradenow.data.requestdata;

import com.ftcsolutions.tradenow.data.BaseDTO;

/**
 * Created by akshay on 24-06-2016.
 */
public class RegisterUserRequest extends BaseDTO {

    private String username;
    private String name;
    private String pwd;
    private String email;
    private String phone;

    public RegisterUserRequest(String username, String name, String pwd, String email, String phone) {
        this.username = username;
        this.name = name;
        this.pwd = pwd;
        this.email = email;
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
