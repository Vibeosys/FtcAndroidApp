package com.vibeosys.tradenow.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.vibeosys.tradenow.R;
import com.vibeosys.tradenow.adapters.TradeDateAdapter;
import com.vibeosys.tradenow.adapters.TradeHistoryDateAdapter;
import com.vibeosys.tradenow.data.adapterdata.SignalDateDTO;
import com.vibeosys.tradenow.data.adapterdata.TradeBackupDateDTO;

import java.util.ArrayList;

public class TradeAlertDateActivity extends BaseActivity {

    private ListView mListTradeData;
    private TradeDateAdapter mAdapter;
    private View mMainTradeDateView;
    private TextView txtError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_alert_date);
        setTitle(getResources().getString(R.string.trade_alert));
        mListTradeData = (ListView) findViewById(R.id.listTradeDate);
        txtError = (TextView) findViewById(R.id.txtError);
        mMainTradeDateView = findViewById(R.id.mainTradeDateView);
        ArrayList<SignalDateDTO> data = new ArrayList<>();
        data = mDbRepository.getSignalDateList("NULL");
        if (data.size() <= 0) {
            txtError.setVisibility(View.VISIBLE);
            mListTradeData.setVisibility(View.GONE);
        } else {
            txtError.setVisibility(View.GONE);
            mListTradeData.setVisibility(View.VISIBLE);
            mAdapter = new TradeDateAdapter(data, getApplicationContext());
            mListTradeData.setAdapter(mAdapter);
            mListTradeData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    SignalDateDTO signalDateDTO = (SignalDateDTO) mAdapter.getItem(position);
                    String date = signalDateDTO.getDate();
                    Intent iTradeAlert = new Intent(getApplicationContext(), TradingAlertActivity.class);
                    iTradeAlert.putExtra("SelectedDate", date);
                    startActivity(iTradeAlert);
                }
            });
        }

    }

    @Override
    protected View getMainView() {
        return this.mMainTradeDateView;
    }
}
