package com.vibeosys.tradenow.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.vibeosys.tradenow.R;
import com.vibeosys.tradenow.adapters.TradeAlertAdapter;
import com.vibeosys.tradenow.data.adapterdata.SignalDataDTO;
import com.vibeosys.tradenow.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;

public class TradingAlertActivity extends BaseActivity implements TradeAlertAdapter.ViewDetailsListener {

    ListView listTreadAlert;
    TradeAlertAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treading_alert);
        String selectedDate = getIntent().getExtras().getString("SelectedDate");

        DateUtils dateUtils = new DateUtils();
        Date signalDate = dateUtils.getFormattedOnlyDate(selectedDate);
        setTitle(dateUtils.getLocalDateInReadableFormat(signalDate));

        listTreadAlert = (ListView) findViewById(R.id.listTradeAlert);
        ArrayList<SignalDataDTO> data = new ArrayList<>();
        data = mDbRepository.getSignalDataList("NULL", selectedDate);
        adapter = new TradeAlertAdapter(data, getApplicationContext());
        adapter.setCustomButtonListner(this);
        listTreadAlert.setAdapter(adapter);
    }

    @Override
    public void onViewClickListener(int id, int value, Object object) {
        startActivity(new Intent(getApplicationContext(), TradeAlertDetailsActivity.class));
    }
}
