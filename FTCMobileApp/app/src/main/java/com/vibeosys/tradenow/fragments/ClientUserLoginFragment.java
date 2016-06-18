package com.vibeosys.tradenow.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.vibeosys.tradenow.MainActivity;
import com.vibeosys.tradenow.R;
import com.vibeosys.tradenow.activities.ForgotPassActivity;
import com.vibeosys.tradenow.activities.TermsAndConditionActivity;
import com.vibeosys.tradenow.adapters.LoginSpinner;

import java.util.ArrayList;

/**
 * Created by akshay on 18-06-2016.
 */
public class ClientUserLoginFragment extends BaseFragment implements View.OnClickListener {

    //Spinner spnClient;
    EditText txtUserName, txtPassword;
    TextView txtForgotPass, txtTerms;
    Button btnLogIn;
    //private LoginSpinner adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_login_client, container, false);
        //spnClient = (Spinner) view.findViewById(R.id.spnClient);
        txtUserName = (EditText) view.findViewById(R.id.txtUserName);
        txtPassword = (EditText) view.findViewById(R.id.txtPassword);
        txtForgotPass = (TextView) view.findViewById(R.id.txtForgotPass);
        txtTerms = (TextView) view.findViewById(R.id.txtTerms);
        btnLogIn = (Button) view.findViewById(R.id.btnLogIn);
        btnLogIn.setOnClickListener(this);
        txtForgotPass.setOnClickListener(this);
        txtTerms.setOnClickListener(this);
        ArrayList<String> spnItem = new ArrayList<>();
        spnItem.add("Abc");
        spnItem.add("Xyz");
        spnItem.add("Pqr");
        spnItem.add("Efg");
       /* adapter = new LoginSpinner(spnItem, getActivity().getApplicationContext());
        spnClient.setAdapter(adapter);*/
        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnLogIn:
                startActivity(new Intent(getActivity().getApplicationContext(), MainActivity.class));
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
