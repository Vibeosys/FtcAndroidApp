package com.vibeosys.tradenow.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.vibeosys.tradenow.MainActivity;
import com.vibeosys.tradenow.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText txtUserName, txtPassword;
    TextView txtRegister, txtForgotPass, txtSubscriber;
    Button btnLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle(getResources().getString(R.string.str_login_activity));
        txtUserName = (EditText) findViewById(R.id.txtUserName);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtRegister = (TextView) findViewById(R.id.txtRegister);
        txtForgotPass = (TextView) findViewById(R.id.txtForgotPass);
        txtSubscriber = (TextView) findViewById(R.id.txtSubscriber);
        btnLogIn = (Button) findViewById(R.id.btnLogIn);
        btnLogIn.setOnClickListener(this);
        txtRegister.setOnClickListener(this);
        txtForgotPass.setOnClickListener(this);
        txtSubscriber.setOnClickListener(this);
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
            case R.id.txtSubscriber:
                startActivity(new Intent(getApplicationContext(), LoginClientActivity.class));
                break;
        }
    }
}
