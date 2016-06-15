package com.vibeosys.ftc.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.vibeosys.ftc.R;
import com.vibeosys.ftc.adapters.TreadAlertAdapter;

import java.util.ArrayList;

public class TreadingAlertActivity extends AppCompatActivity {

    ListView listTreadAlert;
    TreadAlertAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treading_alert);
        listTreadAlert = (ListView) findViewById(R.id.listTreadAlert);
        ArrayList<Integer> data = new ArrayList<>();
        data.add(1);
        data.add(3);
        data.add(2);
        data.add(4);
        data.add(5);
        data.add(6);

        adapter = new TreadAlertAdapter(data, getApplicationContext());
        listTreadAlert.setAdapter(adapter);
    }
}
