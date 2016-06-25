package com.vibeosys.tradenow.data.requestdata;

/**
 * Created by akshay on 25-06-2016.
 */
public class ResetPasswordRequest {

    private String oldPwd;
    private String newPwd;

    public ResetPasswordRequest(String oldPwd, String newPwd) {
        this.oldPwd = oldPwd;
        this.newPwd = newPwd;
    }

    public String getOldPwd() {
        return oldPwd;
    }

    public void setOldPwd(String oldPwd) {
        this.oldPwd = oldPwd;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }
}
