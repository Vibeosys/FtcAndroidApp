package com.vibeosys.tradenow;

import android.app.Application;
import android.util.Log;

import com.onesignal.OneSignal;
import com.vibeosys.tradenow.data.adapterdata.NotificationsDTO;
import com.vibeosys.tradenow.database.DbRepository;
import com.vibeosys.tradenow.utils.DateUtils;
import com.vibeosys.tradenow.utils.SessionManager;

import org.json.JSONObject;

/**
 * Created by akshay on 21-06-2016.
 */
public class TradeNowApplication extends Application {

    private static final String TAG = TradeNowApplication.class.getSimpleName();

    DateUtils dateUtils = new DateUtils();
    private DbRepository mDbRepository = null;
    private static SessionManager mSessionManager = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mSessionManager = SessionManager.getInstance(getApplicationContext());
        mDbRepository = new DbRepository(getApplicationContext(), mSessionManager);
        try {
            OneSignal.startInit(this)
                    .setNotificationOpenedHandler(new ExampleNotificationOpenedHandler())
                    .setAutoPromptLocation(true)
                    .init();
        } catch (Exception e) {
            Log.e(TAG, "Error to open signal");
        }

    }

    private class ExampleNotificationOpenedHandler implements OneSignal.NotificationOpenedHandler {
        /**
         * Callback to implement in your app to handle when a notification is opened from the Android status bar or
         * a new one comes in while the app is running.
         * This method is located in this Application class as an example, you may have any class you wish implement NotificationOpenedHandler and define this method.
         *
         * @param message        The message string the user seen/should see in the Android status bar.
         * @param additionalData The additionalData key value pair section you entered in on onesignal.com.
         * @param isActive       Was the app in the foreground when the notification was received.
         */
        @Override
        public void notificationOpened(String message, JSONObject additionalData, boolean isActive) {
            String additionalMessage = "";
            String title = "";
            try {
                if (additionalData != null) {
                    if (additionalData.has("actionSelected"))
                        additionalMessage += "Pressed ButtonID: " + additionalData.getString("actionSelected");

                    additionalMessage = message + "\nFull additionalData:\n" + additionalData.toString();
                    if (additionalData.has("title")) {
                        title = additionalData.getString("title");
                    }
                }
                String date = dateUtils.getLocalCurrentDate();
                Log.d(TAG, "message:\n" + message + "\nadditionalMessage:\n" + additionalMessage);
                NotificationsDTO notificationsDTO = new NotificationsDTO(title, message, date, 0);
                mDbRepository.insertNotification(notificationsDTO);
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }
}
