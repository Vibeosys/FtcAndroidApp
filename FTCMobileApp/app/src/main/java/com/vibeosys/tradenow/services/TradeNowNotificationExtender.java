package com.vibeosys.tradenow.services;

import android.util.Log;

import com.onesignal.NotificationExtenderService;
import com.onesignal.OSNotificationPayload;
import com.vibeosys.tradenow.MainActivity;
import com.vibeosys.tradenow.activities.NotificationActivity;
import com.vibeosys.tradenow.data.adapterdata.NotificationsDTO;
import com.vibeosys.tradenow.database.DbRepository;
import com.vibeosys.tradenow.utils.DateUtils;
import com.vibeosys.tradenow.utils.NotificationUtil;
import com.vibeosys.tradenow.utils.SessionManager;

import org.json.JSONObject;

/**
 * Created by akshay on 04-07-2016.
 */
public class TradeNowNotificationExtender extends NotificationExtenderService {
    private static final String TAG = TradeNowNotificationExtender.class.getSimpleName();

    DateUtils dateUtils = new DateUtils();
    private DbRepository mDbRepository = null;
    private static SessionManager mSessionManager = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mSessionManager = SessionManager.getInstance(getApplicationContext());
        mDbRepository = new DbRepository(getApplicationContext(), mSessionManager);
    }

    @Override
    protected boolean onNotificationProcessing(OSNotificationPayload notification) {
        JSONObject additionalData = notification.additionalData;
        String additionalMessage = "";
        String title = "";
        try {
            if (additionalData != null) {
                if (additionalData.has("actionSelected"))
                    additionalMessage += "Pressed ButtonID: " + additionalData.getString("actionSelected");

                additionalMessage = notification.message + "\nFull additionalData:\n" + additionalData.toString();
                if (additionalData.has("title")) {
                    title = additionalData.getString("title");
                }
            }
            String date = dateUtils.getLocalCurrentDate();
            Log.d(TAG, "##additionalMessage:\n" + additionalMessage);
            Log.d(TAG, "##message:\n" + notification.message + "\ntitle:\n" + title);
            NotificationsDTO notificationsDTO = new NotificationsDTO(title, notification.message, date, 0);
            mDbRepository.insertNotification(notificationsDTO);
        } catch (Throwable t) {
            t.printStackTrace();
        }
        if (NotificationActivity.adapter != null) {
            NotificationActivity.runOnUI(new Runnable() {
                @Override
                public void run() {

                    try {
                        NotificationActivity.adapter.refresh();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d(TAG, "## notification exception" + e.toString());
                    }
                }
            });
        }
        if (MainActivity.UIHandler != null) {
            MainActivity.runOnUI(new Runnable() {
                @Override
                public void run() {
                    MainActivity.notificationUtil.setBadgeCount(mDbRepository.getUnreadNotificationCount());
                }
            });

        }
        return false;
    }
}
