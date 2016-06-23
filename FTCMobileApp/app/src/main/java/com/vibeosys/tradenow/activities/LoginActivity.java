package com.vibeosys.tradenow.activities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.vibeosys.tradenow.MainActivity;
import com.vibeosys.tradenow.R;
import com.vibeosys.tradenow.adapters.LoginFragmentAdapter;

public class LoginActivity extends BaseActivity {

    TabLayout tab_layout;
    View mainLoginView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);
        getSupportActionBar().hide();
        //setTitle(getResources().getString(R.string.str_login_btn));
        mainLoginView = findViewById(R.id.mainLoginView);
        tab_layout = (TabLayout) findViewById(R.id.tab_layout);
        tab_layout.addTab(tab_layout.newTab().setText("NEW USER"));
        tab_layout.addTab(tab_layout.newTab().setText("SUBSCRIBERS"));
        tab_layout.setTabGravity(TabLayout.GRAVITY_FILL);
        tab_layout.setSelectedTabIndicatorHeight(4);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final LoginFragmentAdapter loginFragmentAdapter = new LoginFragmentAdapter
                (getSupportFragmentManager(), tab_layout.getTabCount());
        viewPager.setAdapter(loginFragmentAdapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab_layout));
        tab_layout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected View getMainView() {
        return this.mainLoginView;
    }
}
