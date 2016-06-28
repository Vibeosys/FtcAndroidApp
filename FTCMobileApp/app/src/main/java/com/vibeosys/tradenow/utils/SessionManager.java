package com.vibeosys.tradenow.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by akshay on 22-06-2016.
 */
public class SessionManager {

    private static final String PROJECT_PREFERENCES = "com.vibeosys.tradenowapp";


    private static SessionManager mSessionManager;
    private static SharedPreferences mProjectSharedPref = null;
    private static Context mContext = null;
    private static PropertyFileReader mPropertyFileReader = null;

    public static SessionManager getInstance(Context context) {
        if (mSessionManager != null)
            return mSessionManager;

        mContext = context;
        mPropertyFileReader = mPropertyFileReader.getInstance(context);
        loadProjectSharedPreferences();
        mSessionManager = new SessionManager();

        return mSessionManager;

    }

    public static SessionManager Instance() throws IllegalArgumentException {
        if (mSessionManager != null)
            return mSessionManager;
        else
            throw new IllegalArgumentException("No instance is yet created");
    }

    private static void loadProjectSharedPreferences() {
        if (mProjectSharedPref == null) {
            mProjectSharedPref = mContext.getSharedPreferences(PROJECT_PREFERENCES, Context.MODE_PRIVATE);
        }

        String versionNumber = mProjectSharedPref.getString(PropertyTypeConstants.VERSION_NUMBER, null);
        Float versionNoValue = versionNumber == null ? 0 : Float.valueOf(versionNumber);

        if (mPropertyFileReader.getVersion() > versionNoValue) {
            boolean sharedPrefChange = addOrUdateSharedPreferences();
            if (!sharedPrefChange)
                Log.e("SharedPref", "No shared preferences are changed");
        }
    }

    private static boolean addOrUdateSharedPreferences() {

        SharedPreferences.Editor editor = mProjectSharedPref.edit();
        editor.putString(PropertyTypeConstants.SIGNAL_URL, mPropertyFileReader.getSignalUrl());
        editor.putString(PropertyTypeConstants.CLIENT_LOGIN_URL, mPropertyFileReader.getClientLoginUrl());
        editor.putString(PropertyTypeConstants.PAGES_URL, mPropertyFileReader.getPagesUrl());
        editor.putString(PropertyTypeConstants.DATABASE_DEVICE_FULLPATH, mPropertyFileReader.getDatabaseDeviceFullPath());
        editor.putString(PropertyTypeConstants.DATABASE_DIR_PATH, mPropertyFileReader.getDatabaseDirPath());
        editor.putString(PropertyTypeConstants.DATABASE_FILE_NAME, mPropertyFileReader.getDatabaseFileName());
        editor.putString(PropertyTypeConstants.VERSION_NUMBER, String.valueOf(mPropertyFileReader.getVersion()));
        editor.putInt(PropertyTypeConstants.DATABASE_VERSION_NUMBER, mPropertyFileReader.getDbVersion());
        editor.putString(PropertyTypeConstants.USER_NAME_AVAILABLE, mPropertyFileReader.getUserNameAvail());
        editor.putString(PropertyTypeConstants.USER_REGISTRATION, mPropertyFileReader.getUserRegistrationUrl());
        editor.putString(PropertyTypeConstants.USER_LOGIN_URL, mPropertyFileReader.getUserLoginUrl());
        editor.putString(PropertyTypeConstants.USER_FORGOT_PASS, mPropertyFileReader.getUserForgotPassUrl());
        editor.putString(PropertyTypeConstants.CLIENT_FORGOT_PASS, mPropertyFileReader.getClientForgotPassUrl());
        editor.putString(PropertyTypeConstants.RESET_PASS, mPropertyFileReader.getResetPassUrl());
        editor.putString(PropertyTypeConstants.GET_PROFILE_URL, mPropertyFileReader.getProfileUrl());
        editor.putString(PropertyTypeConstants.GET_UPDATE_PROFILE_URL, mPropertyFileReader.getUpdateProfileUrl());
        editor.putString(PropertyTypeConstants.GET_TRADE_BACKUP_URL, mPropertyFileReader.getTradeBackUpUrl());
        editor.apply();
        return true;
    }

    private SessionManager() {
    }

    public String getDatabaseDeviceFullPath() {
        return mProjectSharedPref.getString(PropertyTypeConstants.DATABASE_DEVICE_FULLPATH, null);
    }

    public String getSignalUrl() {
        return mProjectSharedPref.getString(PropertyTypeConstants.SIGNAL_URL, null);
    }

    public String getPageUrl() {
        return mProjectSharedPref.getString(PropertyTypeConstants.PAGES_URL, null);
    }

    public int getDatabaseVersion() {
        Log.d("DB version", "##" + mProjectSharedPref.getInt(PropertyTypeConstants.DATABASE_VERSION_NUMBER, 0));
        return mProjectSharedPref.getInt(PropertyTypeConstants.DATABASE_VERSION_NUMBER, 0);
    }

    public long getLastSyncDate() {
        return mProjectSharedPref.getLong(PropertyTypeConstants.DATE_TO_SYNC, 0);
    }

    public void setLastSyncDate(long date) {
        setValuesInSharedPrefs(PropertyTypeConstants.DATE_TO_SYNC, date);
    }

    public long getLastBackupSyncDate() {
        return mProjectSharedPref.getLong(PropertyTypeConstants.TRADE_BACKUP_SYNC, 0);
    }

    public void setLastBackupSyncDate(long date) {
        setValuesInSharedPrefs(PropertyTypeConstants.TRADE_BACKUP_SYNC, date);
    }

    private static void setValuesInSharedPrefs(String sharedPrefKey, String sharedPrefValue) {
        SharedPreferences.Editor editor = mProjectSharedPref.edit();
        editor.putString(sharedPrefKey, sharedPrefValue);
        editor.apply();
    }

    private static void setValuesInSharedPrefs(String sharedPrefKey, int sharedPrefValue) {
        SharedPreferences.Editor editor = mProjectSharedPref.edit();
        editor.putInt(sharedPrefKey, sharedPrefValue);
        editor.apply();
    }

    private static void setValuesInSharedPrefs(String sharedPrefKey, long sharedPrefValue) {
        SharedPreferences.Editor editor = mProjectSharedPref.edit();
        editor.putLong(sharedPrefKey, sharedPrefValue);
        editor.apply();
    }

    public String getClientLoginUrl() {
        return mProjectSharedPref.getString(PropertyTypeConstants.CLIENT_LOGIN_URL, null);
    }

    public String getAvailableUserUrl() {
        return mProjectSharedPref.getString(PropertyTypeConstants.USER_NAME_AVAILABLE, null);
    }

    public String getRegisterUserUrl() {
        return mProjectSharedPref.getString(PropertyTypeConstants.USER_REGISTRATION, null);
    }


    public void setUserId(long userId) {
        setValuesInSharedPrefs(PropertyTypeConstants.USER_ID, userId);
    }

    public long getUserId() {
        return mProjectSharedPref.getLong(PropertyTypeConstants.USER_ID, 0);
    }

    public void setFullName(String fullName) {
        setValuesInSharedPrefs(PropertyTypeConstants.USER_FULL_NAME, fullName);
    }

    public String getUserFullName() {
        return mProjectSharedPref.getString(PropertyTypeConstants.USER_FULL_NAME, null);
    }

    public void setUserName(String userName) {
        setValuesInSharedPrefs(PropertyTypeConstants.USER_USER_NAME, userName);
    }

    public String getUserName() {
        return mProjectSharedPref.getString(PropertyTypeConstants.USER_USER_NAME, null);
    }

    public void setUserPassword(String userPassword) {
        setValuesInSharedPrefs(PropertyTypeConstants.USER_PASS, userPassword);
    }

    public String getUserPassword() {
        return mProjectSharedPref.getString(PropertyTypeConstants.USER_PASS, null);
    }

    public void setUserEmailId(String userEmailId) {
        setValuesInSharedPrefs(PropertyTypeConstants.USER_EMAIL, userEmailId);
    }

    public String getUserEmailId() {
        return mProjectSharedPref.getString(PropertyTypeConstants.USER_EMAIL, null);
    }

    public void setSubId(long subId) {
        setValuesInSharedPrefs(PropertyTypeConstants.USER_SUB_ID, subId);
    }

    public long getSubId() {
        return mProjectSharedPref.getLong(PropertyTypeConstants.USER_SUB_ID, 0);
    }

    public String getUserLoginUrl() {
        return mProjectSharedPref.getString(PropertyTypeConstants.USER_LOGIN_URL, null);
    }

    public String getForgotPassUserUrl() {
        return mProjectSharedPref.getString(PropertyTypeConstants.USER_FORGOT_PASS, null);
    }

    public String getForgotPassClientUrl() {
        return mProjectSharedPref.getString(PropertyTypeConstants.CLIENT_FORGOT_PASS, null);
    }

    public String getResetPassUrl() {
        return mProjectSharedPref.getString(PropertyTypeConstants.RESET_PASS, null);
    }

    public String getProfileUrl() {
        return mProjectSharedPref.getString(PropertyTypeConstants.GET_PROFILE_URL, null);
    }


    public String getUpdateProfileUrl() {
        return mProjectSharedPref.getString(PropertyTypeConstants.GET_UPDATE_PROFILE_URL, null);
    }

    public String getTradeBackupUrl() {
        return mProjectSharedPref.getString(PropertyTypeConstants.GET_TRADE_BACKUP_URL, null);
    }
}
