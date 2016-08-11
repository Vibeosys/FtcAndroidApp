package com.ftcsolutions.tradenow.data.requestdata;

import com.ftcsolutions.tradenow.data.BaseDTO;

/**
 * Created by akshay on 24-06-2016.
 */
public class ForgotPasswordRequest extends BaseDTO {

    private String username;
    private String email;

    public ForgotPasswordRequest(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
