package com.vibeosys.ftc.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.vibeosys.ftc.MainActivity;
import com.vibeosys.ftc.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText txtUserName, txtPassword;
    TextView txtRegister, txtForgotPass;
    Button btnLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtUserName = (EditText) findViewById(R.id.txtUserName);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtRegister = (TextView) findViewById(R.id.txtRegister);
        txtForgotPass = (TextView) findViewById(R.id.txtForgotPass);
        btnLogIn = (Button) findViewById(R.id.btnLogIn);
        btnLogIn.setOnClickListener(this);
        txtRegister.setOnClickListener(this);
        txtForgotPass.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnLogIn:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                break;
            case R.id.txtRegister:
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                break;
            case R.id.txtForgotPass:
                startActivity(new Intent(getApplicationContext(), ForgotPassActivity.class));
                break;
        }
    }
}
