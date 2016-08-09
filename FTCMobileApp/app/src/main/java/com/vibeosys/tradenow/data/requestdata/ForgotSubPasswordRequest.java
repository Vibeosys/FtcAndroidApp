package com.vibeosys.tradenow.data.requestdata;

/**
 * Created by akshay on 24-06-2016.
 */
public class ForgotSubPasswordRequest {

    private String username;
    private String email;
    private String subscriberId;

    public ForgotSubPasswordRequest(String username, String email, String subscriberId) {
        this.username = username;
        this.email = email;
        this.subscriberId = subscriberId;
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

    public String getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
    }
}
