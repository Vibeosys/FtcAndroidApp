package com.vibeosys.tradenow.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.vibeosys.tradenow.R;
import com.vibeosys.tradenow.adapters.TradeHistoryAdapter;
import com.vibeosys.tradenow.data.adapterdata.SignalDataDTO;
import com.vibeosys.tradenow.data.adapterdata.SignalDateDTO;
import com.vibeosys.tradenow.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;

public class TradeHistoryActivity extends BaseActivity implements TradeHistoryAdapter.ViewDetailsListener {

    private ListView listTradeHistory;
    private TradeHistoryAdapter adapter;
    private TextView txtError;
    private View mainTradHistoryView;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_history);
        String selectedDate = getIntent().getExtras().getString("SelectedDate");

        DateUtils dateUtils = new DateUtils();
        Date signalDate = dateUtils.getFormattedOnlyDate(selectedDate);
        setTitle("As On " + dateUtils.getLocalDateInReadableFormat(signalDate));

        listTradeHistory = (ListView) findViewById(R.id.listTradeHistory);
        txtError = (TextView) findViewById(R.id.txtError);
        mainTradHistoryView = findViewById(R.id.mainTradHistoryView);

        ArrayList<SignalDataDTO> data = new ArrayList<>();
        data = mDbRepository.getSignalDataList("NULL", selectedDate);
        if (data.size() <= 0) {
            txtError.setVisibility(View.VISIBLE);
            listTradeHistory.setVisibility(View.GONE);
        } else {
            txtError.setVisibility(View.GONE);
            listTradeHistory.setVisibility(View.VISIBLE);
            adapter = new TradeHistoryAdapter(data, getApplicationContext());
            adapter.setCustomButtonListner(this);
            listTradeHistory.setAdapter(adapter);
        }
    }

    @Override
    protected View getMainView() {
        return this.mainTradHistoryView;
    }

    @Override
    public void onViewClickListener(int id, int value, Object object) {
        startActivity(new Intent(getApplicationContext(), TradeHistoryDetailsActivity.class));
    }
}
