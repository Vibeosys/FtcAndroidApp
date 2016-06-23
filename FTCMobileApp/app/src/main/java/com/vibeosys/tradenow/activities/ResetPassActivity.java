package com.vibeosys.tradenow.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.vibeosys.tradenow.R;

public class ResetPassActivity extends BaseActivity {

    private View mainResetPassView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass);
        setTitle(getResources().getString(R.string.reset_pass));
        mainResetPassView = findViewById(R.id.mainResetPassView);
    }

    @Override
    protected View getMainView() throws NullPointerException {
        return this.mainResetPassView;
    }
}
