package com.ftcsolutions.tradenow.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.ftcsolutions.tradenow.R;
import com.ftcsolutions.tradenow.adapters.TradeHistoryAdapter;
import com.ftcsolutions.tradenow.data.adapterdata.TradeBackupDataDTO;
import com.ftcsolutions.tradenow.utils.DateUtils;

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

        ArrayList<TradeBackupDataDTO> data = new ArrayList<>();
        data = mDbRepository.getTradeBackupDataList(selectedDate);
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
    public void onViewClickListener(int id, long value, Object object) {
        Intent details = new Intent(getApplicationContext(), TradeDetailViewActivity.class);
        details.putExtra("ticketNo", value);
        startActivity(details);
    }
}
