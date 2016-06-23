package com.vibeosys.tradenow.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.vibeosys.tradenow.R;

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
