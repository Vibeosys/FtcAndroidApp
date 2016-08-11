package com.ftcsolutions.tradenow.utils;

import android.content.Context;
import android.content.Intent;

import com.ftcsolutions.tradenow.activities.LoginActivity;
import com.ftcsolutions.tradenow.data.UserDTO;

/**
 * Created by akshay on 24-06-2015.
 */
public class UserAuth {

    public static boolean isUserLoggedIn(Context context, String userName, String password) {
        if (password == null || password == "" || userName == null || userName == "") {
            Intent theLoginIntent = new Intent(context, LoginActivity.class);
            //theLoginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            theLoginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            theLoginIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            context.startActivity(theLoginIntent);
            return false;
        }
        return true;
    }

    public static boolean isUserLoggedIn(Context context) {
        String theUserPassword = SessionManager.Instance().getUserPassword();
        String theUserName = SessionManager.Instance().getUserName();
        return isUserLoggedIn(context, theUserName, theUserPassword);
    }

    public static boolean isUserLoggedIn() {
        String theUserPassword = SessionManager.Instance().getUserPassword();
        String theUserName = SessionManager.Instance().getUserName();
        //String theUserPhotoURL = SessionManager.Instance().getUserPhotoUrl();

        if (theUserPassword == null || theUserPassword == "" || theUserName == null || theUserName == "") {
            return false;
        }
        return true;
    }

    public void saveAuthenticationInfo(UserDTO userInfo, final Context context) {
        if (userInfo == null)
            return;

        if (userInfo.getEmail() == null || userInfo.getEmail() == "" ||
                userInfo.getFullName() == null || userInfo.getFullName() == "")
            return;

        SessionManager theSessionManager = SessionManager.getInstance(context);
        theSessionManager.setUserId(userInfo.getUserId());
        theSessionManager.setFullName(userInfo.getFullName());
        theSessionManager.setUserName(userInfo.getUsername());
        theSessionManager.setUserPassword(userInfo.getPwd());
        theSessionManager.setUserEmailId(userInfo.getEmail());
        theSessionManager.setSubId(userInfo.getSubscriberId());
    }

    public static boolean CleanAuthenticationInfo() {

        SessionManager theSessionManager = SessionManager.Instance();
        theSessionManager.setUserId(0);
        theSessionManager.setFullName(null);
        theSessionManager.setUserName(null);
        theSessionManager.setUserPassword(null);
        theSessionManager.setUserEmailId(null);
        theSessionManager.setSubId(null);
        theSessionManager.setLastSyncDate(0);
        theSessionManager.setLastBackupSyncDate(0);
        return true;
    }

}
