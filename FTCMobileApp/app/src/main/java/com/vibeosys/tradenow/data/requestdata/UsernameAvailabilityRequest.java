package com.vibeosys.tradenow.data.requestdata;

/**
 * Created by akshay on 24-06-2016.
 */
public class UsernameAvailabilityRequest {

    private String username;

    public UsernameAvailabilityRequest(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
