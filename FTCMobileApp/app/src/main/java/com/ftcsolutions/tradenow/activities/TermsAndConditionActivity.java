package com.ftcsolutions.tradenow.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.ftcsolutions.tradenow.R;

public class TermsAndConditionActivity extends AppCompatActivity {
private TextView mTermsAndConditions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_condition);
        mTermsAndConditions = (TextView) findViewById(R.id.txtDescription);
        setTitle(getResources().getString(R.string.terms_n_condition));
        mTermsAndConditions.setText(Html.fromHtml(getResources().getString(R.string.terms_and_condition)));
    }
}
