package com.ftcsolutions.tradenow.activities;

import android.os.Handler;
import android.os.Looper;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.ftcsolutions.tradenow.R;
import com.ftcsolutions.tradenow.adapters.NotificationAdapter;
import com.ftcsolutions.tradenow.data.adapterdata.NotificationsDTO;

import java.util.ArrayList;

public class NotificationActivity extends BaseActivity {

    private ListView listNotification;
    public static NotificationAdapter adapter;
    public static Handler UIHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        setTitle(getResources().getString(R.string.action_notification));
        listNotification = (ListView) findViewById(R.id.listNotification);
        ArrayList<NotificationsDTO> data = new ArrayList<>();
        data = mDbRepository.getNotification();
        adapter = new NotificationAdapter(data, getApplicationContext(), mDbRepository);
        listNotification.setAdapter(adapter);
        if (data != null)
            mDbRepository.updateNotification(data);
    }

    @Override
    protected View getMainView() {
        return null;
    }

    static {
        UIHandler = new Handler(Looper.getMainLooper());
    }

    public static void runOnUI(Runnable runnable) {
        UIHandler.post(runnable);
    }
}
