package com.vibeosys.tradenow.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.vibeosys.tradenow.R;
import com.vibeosys.tradenow.adapters.TradeDateAdapter;
import com.vibeosys.tradenow.adapters.TradeHistoryDateAdapter;
import com.vibeosys.tradenow.data.adapterdata.SignalDateDTO;
import com.vibeosys.tradenow.data.adapterdata.TradeBackupDateDTO;

import java.util.ArrayList;

public class TradeHistoryDateActivity extends BaseActivity {

    private ListView listTradeData;
    private TradeHistoryDateAdapter adapter;
    private View mainTradHistoryDateView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_history_date_activiy);
        setTitle(getResources().getString(R.string.trade_history));
        listTradeData = (ListView) findViewById(R.id.listTradeDate);
        mainTradHistoryDateView = findViewById(R.id.mainTradHistoryDateView);
        ArrayList<TradeBackupDateDTO> data = new ArrayList<>();
        data = mDbRepository.getTradeDateList();
        adapter = new TradeHistoryDateAdapter(data, getApplicationContext());
        listTradeData.setAdapter(adapter);
        listTradeData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TradeBackupDateDTO backupDate = (TradeBackupDateDTO) adapter.getItem(position);
                String date = backupDate.getDate();
                Intent iTradeAlert = new Intent(getApplicationContext(), TradeHistoryActivity.class);
                iTradeAlert.putExtra("SelectedDate", date);
                startActivity(iTradeAlert);
            }
        });
    }

    @Override
    protected View getMainView() {
        return this.mainTradHistoryDateView;
    }
}
