package com.vibeosys.tradenow.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.vibeosys.tradenow.R;
import com.vibeosys.tradenow.adapters.TradeDateAdapter;
import com.vibeosys.tradenow.data.adapterdata.SignalDateDTO;

import java.util.ArrayList;

public class TradeHistoryDateActivity extends BaseActivity {
    ListView listTradeData;
    TradeDateAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_history_date_activiy);
        setTitle(getResources().getString(R.string.trade_history));
        listTradeData = (ListView) findViewById(R.id.listTradeDate);
        ArrayList<SignalDateDTO> data = new ArrayList<>();
        data = mDbRepository.getSignalDateList("NULL");
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
