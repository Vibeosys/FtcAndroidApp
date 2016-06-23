package com.vibeosys.tradenow.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.vibeosys.tradenow.MainActivity;
import com.vibeosys.tradenow.R;
import com.vibeosys.tradenow.adapters.LoginSpinner;

import java.util.ArrayList;

public class LoginClientActivity extends BaseActivity implements View.OnClickListener {

    // Spinner spnClient;
    EditText txtUserName, txtPassword;
    TextView txtForgotPass;
    Button btnLogIn;
    //private LoginSpinner adapter;
    View mainClientLoginView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_client);
        setTitle(getResources().getString(R.string.str_activity_subscriber_login));
        // spnClient = (Spinner) findViewById(R.id.spnClient);
        mainClientLoginView = findViewById(R.id.mainClientLoginView);
        txtUserName = (EditText) findViewById(R.id.txtUserName);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtForgotPass = (TextView) findViewById(R.id.txtForgotPass);
        btnLogIn = (Button) findViewById(R.id.btnLogIn);
        btnLogIn.setOnClickListener(this);
        txtForgotPass.setOnClickListener(this);
        ArrayList<String> spnItem = new ArrayList<>();
        spnItem.add("Abc");
        spnItem.add("Xyz");
        spnItem.add("Pqr");
        spnItem.add("Efg");
       /* adapter = new LoginSpinner(spnItem, getApplicationContext());
        spnClient.setAdapter(adapter);*/
    }

    @Override
    protected View getMainView() {
        return this.mainClientLoginView;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnLogIn:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                break;
            case R.id.txtForgotPass:
                startActivity(new Intent(getApplicationContext(), ForgotPassActivity.class));
                break;
        }
    }
}
