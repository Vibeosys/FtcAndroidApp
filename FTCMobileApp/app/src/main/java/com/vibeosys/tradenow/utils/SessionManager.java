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
}
