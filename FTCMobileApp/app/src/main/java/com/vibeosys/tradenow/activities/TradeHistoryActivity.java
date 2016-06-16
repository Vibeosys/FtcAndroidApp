package com.vibeosys.tradenow.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.vibeosys.tradenow.R;
import com.vibeosys.tradenow.adapters.TradeHistoryAdapter;

import java.util.ArrayList;

public class TradeHistoryActivity extends AppCompatActivity {

    ListView listTradeHistory;
    TradeHistoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_history);
        setTitle(getResources().getString(R.string.trade_history));
        listTradeHistory = (ListView) findViewById(R.id.listTradeHistory);
        ArrayList<Integer> data = new ArrayList<>();
        data.add(1);
        data.add(3);
        data.add(2);
        data.add(4);
        data.add(5);
        data.add(6);

        adapter = new TradeHistoryAdapter(data, getApplicationContext());
        listTradeHistory.setAdapter(adapter);
    }
}
