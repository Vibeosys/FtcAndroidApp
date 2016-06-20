package com.vibeosys.tradenow.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.vibeosys.tradenow.R;
import com.vibeosys.tradenow.adapters.TradeDateAdapter;

import java.util.ArrayList;

public class TradeHistoryDateActivity extends AppCompatActivity {
    ListView listTradeData;
    TradeDateAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_history_date_activiy);
        setTitle(getResources().getString(R.string.trade_history));
        listTradeData = (ListView) findViewById(R.id.listTradeDate);
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
        adapter = new TradeDateAdapter(data, getApplicationContext());
        listTradeData.setAdapter(adapter);
        listTradeData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getApplicationContext(), TradeHistoryActivity.class));
            }
        });
    }
}
