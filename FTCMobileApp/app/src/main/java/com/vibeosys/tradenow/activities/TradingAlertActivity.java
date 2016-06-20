package com.vibeosys.tradenow.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.vibeosys.tradenow.R;
import com.vibeosys.tradenow.adapters.TradeAlertAdapter;

import java.util.ArrayList;

public class TradingAlertActivity extends AppCompatActivity implements TradeAlertAdapter.ViewDetailsListener {

    ListView listTreadAlert;
    TradeAlertAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treading_alert);
        setTitle("Monday 20th June 2016");
        listTreadAlert = (ListView) findViewById(R.id.listTradeAlert);
        ArrayList<Integer> data = new ArrayList<>();
        data.add(1);
        data.add(3);
        data.add(2);
        data.add(4);
        data.add(5);
        data.add(6);
        data.add(1);
        data.add(3);
        data.add(2);
        data.add(4);
        data.add(5);
        data.add(6);
        data.add(1);
        data.add(3);
        data.add(2);
        data.add(4);
        data.add(5);
        data.add(6);
        data.add(1);
        data.add(3);
        data.add(2);
        data.add(4);
        data.add(5);
        data.add(6);
        data.add(1);
        data.add(3);
        data.add(2);
        data.add(4);
        data.add(5);
        data.add(6);
        adapter = new TradeAlertAdapter(data, getApplicationContext());
        adapter.setCustomButtonListner(this);
        listTreadAlert.setAdapter(adapter);
    }

    @Override
    public void onViewClickListener(int id, int value, Object object) {
        startActivity(new Intent(getApplicationContext(), TradeAlertDetailsActivity.class));
    }
}
