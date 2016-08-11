package com.ftcsolutions.tradenow.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.ftcsolutions.tradenow.R;
import com.ftcsolutions.tradenow.adapters.TradeHistoryDateAdapter;
import com.ftcsolutions.tradenow.data.adapterdata.TradeBackupDateDTO;

import java.util.ArrayList;

public class TradeHistoryDateActivity extends BaseActivity {

    private ListView listTradeData;
    private TradeHistoryDateAdapter adapter;
    private TextView txtError;
    private View mainTradHistoryDateView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_history_date_activiy);
        setTitle(getResources().getString(R.string.trade_history));
        listTradeData = (ListView) findViewById(R.id.listTradeDate);
        txtError = (TextView) findViewById(R.id.txtError);
        mainTradHistoryDateView = findViewById(R.id.mainTradHistoryDateView);
        ArrayList<TradeBackupDateDTO> data = new ArrayList<>();
        data = mDbRepository.getTradeDateList();
        if (data.size() <= 0) {
            txtError.setVisibility(View.VISIBLE);
            listTradeData.setVisibility(View.GONE);
        } else {
            txtError.setVisibility(View.GONE);
            listTradeData.setVisibility(View.VISIBLE);
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

    }

    @Override
    protected View getMainView() {
        return this.mainTradHistoryDateView;
    }
}
