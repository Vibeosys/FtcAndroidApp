package com.vibeosys.tradenow.utils;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.InputStream;
import java.util.Properties;

/**
 * Created by akshay on 22-06-2016.
 */
public class PropertyFileReader {

    private static PropertyFileReader mPropertyFileReader = null;
    private static Context mContext;
    protected static Properties mProperties;

    public static PropertyFileReader getInstance(Context context) {
        if (mPropertyFileReader != null)
            return mPropertyFileReader;

        mContext = context;
        mProperties = getProperties();
        mPropertyFileReader = new PropertyFileReader();
        return mPropertyFileReader;
    }

    protected static Properties getProperties() {
        try {
            AssetManager assetManager = mContext.getAssets();
            InputStream inputStream = assetManager.open("app.properties");
            mProperties = new Properties();
            mProperties.load(inputStream);

        } catch (Exception e) {
            System.out.print(e.getMessage());
        }

        return mProperties;
    }

    protected String getEndPointUri() {
        return mProperties.getProperty(PropertyTypeConstants.API_ENDPOINT_URI);
    }

    public String getDatabaseDeviceFullPath() {
        return mProperties.getProperty(PropertyTypeConstants.DATABASE_DEVICE_FULLPATH);
    }

    public float getVersion() {
        String versionNumber = mProperties.getProperty(PropertyTypeConstants.VERSION_NUMBER);
        return Float.valueOf(versionNumber);
    }


    public String getDatabaseDirPath() {
        return mProperties.getProperty(PropertyTypeConstants.DATABASE_DIR_PATH);
    }

    public String getDatabaseFileName() {
        return mProperties.getProperty(PropertyTypeConstants.DATABASE_FILE_NAME);
    }

    public String getSignalUrl() {
        return getEndPointUri() + mProperties.getProperty(PropertyTypeConstants.SIGNAL_URL);
    }

    public String getPagesUrl() {
        return getEndPointUri() + mProperties.getProperty(PropertyTypeConstants.PAGES_URL);
    }

    public int getDbVersion() {
        String versionNumber = mProperties.getProperty(PropertyTypeConstants.DATABASE_VERSION_NUMBER);
        return Integer.valueOf(versionNumber);
    }

    public String getClientLoginUrl() {
        return getEndPointUri() + mProperties.getProperty(PropertyTypeConstants.CLIENT_LOGIN_URL);
    }

    public String getUserNameAvail() {
        return getEndPointUri() + mProperties.getProperty(PropertyTypeConstants.USER_NAME_AVAILABLE);
    }

    public String getUserRegistrationUrl() {
        return getEndPointUri() + mProperties.getProperty(PropertyTypeConstants.USER_REGISTRATION);
    }

    public String getUserLoginUrl() {
        return getEndPointUri() + mProperties.getProperty(PropertyTypeConstants.USER_LOGIN_URL);
    }

    public String getUserForgotPassUrl() {
        return getEndPointUri() + mProperties.getProperty(PropertyTypeConstants.USER_FORGOT_PASS);
    }

    public String getClientForgotPassUrl() {
        return getEndPointUri() + mProperties.getProperty(PropertyTypeConstants.CLIENT_FORGOT_PASS);
    }

    public String getResetPassUrl() {
        return getEndPointUri() + mProperties.getProperty(PropertyTypeConstants.RESET_PASS);
    }

    public String getProfileUrl() {
        return getEndPointUri() + mProperties.getProperty(PropertyTypeConstants.GET_PROFILE_URL);
    }

    public String getUpdateProfileUrl() {
        return getEndPointUri() + mProperties.getProperty(PropertyTypeConstants.GET_UPDATE_PROFILE_URL);
    }

    public String getTradeBackUpUrl() {
        return getEndPointUri() + mProperties.getProperty(PropertyTypeConstants.GET_TRADE_BACKUP_URL);
    }

    public String getFirstPagesUrl() {
        return getEndPointUri() + mProperties.getProperty(PropertyTypeConstants.GET_PAGES_URL);
    }
}
