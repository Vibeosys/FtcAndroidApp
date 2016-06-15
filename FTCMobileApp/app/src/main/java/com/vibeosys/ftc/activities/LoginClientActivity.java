package com.vibeosys.ftc.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.vibeosys.ftc.R;

public class LoginClientActivity extends AppCompatActivity {

    Spinner spnClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_client);
        spnClient = (Spinner) findViewById(R.id.spnClient);
        String[] spnItem = {"Abc", "Xyz", "Pqr", "Efg"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_dropdown_item_1line, spnItem);
        spnClient.setAdapter(adapter);
    }
}
