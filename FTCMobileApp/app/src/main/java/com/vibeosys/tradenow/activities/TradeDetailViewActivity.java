package com.vibeosys.tradenow.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.vibeosys.tradenow.R;

public class TradeDetailViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_detail_view);
        setTitle("INR TO USD");
    }

    @Override
    protected View getMainView() {
        return null;
    }
}
