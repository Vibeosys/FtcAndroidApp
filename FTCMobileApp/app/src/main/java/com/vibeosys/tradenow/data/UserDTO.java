package com.vibeosys.tradenow.data;

/**
 * Created by akshay on 24-06-2016.
 */
public class UserDTO {

    private long mUserId;
    private String mFullName;
    private String mUsername;
    private String mPwd;
    private String mEmail;
    private String mSubscriberId;


    public UserDTO(long mUserId, String mFullName, String mUsername, String mPwd, String mEmail) {
        this.mUserId = mUserId;
        this.mFullName = mFullName;
        this.mUsername = mUsername;
        this.mPwd = mPwd;
        this.mEmail = mEmail;
    }

    public UserDTO(long mUserId, String mFullName, String mUsername, String mPwd, String mEmail, String mSubscriberId) {
        this.mUserId = mUserId;
        this.mFullName = mFullName;
        this.mUsername = mUsername;
        this.mPwd = mPwd;
        this.mEmail = mEmail;
        this.mSubscriberId = mSubscriberId;
    }

    public long getUserId() {
        return mUserId;
    }

    public void setUserId(long mUserId) {
        this.mUserId = mUserId;
    }

    public String getFullName() {
        return mFullName;
    }

    public void setFullName(String mFullName) {
        this.mFullName = mFullName;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    public String getPwd() {
        return mPwd;
    }

    public void setPwd(String mPwd) {
        this.mPwd = mPwd;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getSubscriberId() {
        return mSubscriberId;
    }

    public void setSubscriberId(String mSubscriberId) {
        this.mSubscriberId = mSubscriberId;
    }
}
