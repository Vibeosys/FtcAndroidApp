package com.ftcsolutions.tradenow.activities;

import android.os.Bundle;
import android.view.View;

public class TradeAlertDetailsActivity extends TradeDetailViewActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_trade_alert_details);
        setTitle("INR TO USD Forex Details");
    }

    @Override
    protected View getMainView() {
        return null;
    }
}
