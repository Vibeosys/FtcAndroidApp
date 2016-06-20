package com.vibeosys.tradenow.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.vibeosys.tradenow.R;
import com.vibeosys.tradenow.adapters.TradeHistoryAdapter;

import java.util.ArrayList;

public class TradeHistoryActivity extends BaseActivity implements TradeHistoryAdapter.ViewDetailsListener {

    ListView listTradeHistory;
    TradeHistoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_history);
        setTitle("Monday 20th June 2016");
        listTradeHistory = (ListView) findViewById(R.id.listTradeHistory);
        ArrayList<Integer> data = new ArrayList<>();
        data.add(1);
        data.add(3);
        data.add(2);
        data.add(4);
        data.add(5);
        data.add(6);

        adapter = new TradeHistoryAdapter(data, getApplicationContext());
        adapter.setCustomButtonListner(this);
        listTradeHistory.setAdapter(adapter);
    }

    @Override
    public void onViewClickListener(int id, int value, Object object) {
        startActivity(new Intent(getApplicationContext(), TradeHistoryDetailsActivity.class));
    }
}
