package com.vibeosys.tradenow.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.vibeosys.tradenow.MainActivity;
import com.vibeosys.tradenow.R;
import com.vibeosys.tradenow.activities.ForgotPassActivity;
import com.vibeosys.tradenow.activities.LoginClientActivity;
import com.vibeosys.tradenow.activities.RegisterActivity;
import com.vibeosys.tradenow.activities.TermsAndConditionActivity;

/**
 * Created by akshay on 18-06-2016.
 */
public class NewUserLoginFragment extends BaseFragment implements View.OnClickListener {
    EditText txtUserName, txtPassword;
    TextView txtRegister, txtForgotPass, txtTerms;
    Button btnLogIn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_login, container, false);
        //setTitle(getResources().getString(R.string.str_login_activity));
        txtUserName = (EditText) view.findViewById(R.id.txtUserName);
        txtPassword = (EditText) view.findViewById(R.id.txtPassword);
        txtRegister = (TextView) view.findViewById(R.id.txtRegister);
        txtForgotPass = (TextView) view.findViewById(R.id.txtForgotPass);
        txtTerms = (TextView) view.findViewById(R.id.txtTerms);
        btnLogIn = (Button) view.findViewById(R.id.btnLogIn);
        btnLogIn.setOnClickListener(this);
        txtRegister.setOnClickListener(this);
        txtForgotPass.setOnClickListener(this);
        txtTerms.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnLogIn:
                startActivity(new Intent(getActivity().getApplicationContext(), MainActivity.class));
                break;
            case R.id.txtRegister:
                startActivity(new Intent(getActivity().getApplicationContext(), RegisterActivity.class));
                break;
            case R.id.txtForgotPass:
                startActivity(new Intent(getActivity().getApplicationContext(), ForgotPassActivity.class));
                break;
            case R.id.txtTerms:
                startActivity(new Intent(getActivity().getApplicationContext(), TermsAndConditionActivity.class));
                break;
        }
    }
}
