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

public class TradeAlertDateActivity extends BaseActivity {

    ListView listTradeData;
    TradeDateAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_alert_date);
        setTitle(getResources().getString(R.string.trade_alert));
        listTradeData = (ListView) findViewById(R.id.listTradeDate);
        ArrayList<SignalDateDTO> data = new ArrayList<>();
        data = mDbRepository.getSignalDateList("NULL");
        adapter = new TradeDateAdapter(data, getApplicationContext());
        listTradeData.setAdapter(adapter);
        listTradeData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SignalDateDTO signalDateDTO = (SignalDateDTO) adapter.getItem(position);
                String date = signalDateDTO.getDate();
                Intent iTradeAlert = new Intent(getApplicationContext(), TradingAlertActivity.class);
                iTradeAlert.putExtra("SelectedDate", date);
                startActivity(iTradeAlert);
            }
        });

    }
}
