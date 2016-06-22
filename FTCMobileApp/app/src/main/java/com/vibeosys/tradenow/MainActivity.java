package com.vibeosys.tradenow;

import android.content.Intent;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.vibeosys.tradenow.activities.DemoActivity;
import com.vibeosys.tradenow.activities.LoginActivity;
import com.vibeosys.tradenow.activities.MyProfileActivity;
import com.vibeosys.tradenow.activities.NewsActivity;
import com.vibeosys.tradenow.activities.NotificationActivity;
import com.vibeosys.tradenow.activities.ResetPassActivity;
import com.vibeosys.tradenow.activities.TradeAlertDateActivity;
import com.vibeosys.tradenow.activities.TradeHistoryDateActivity;
import com.vibeosys.tradenow.utils.NotificationUtil;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.action_notification);
        LayerDrawable icon = (LayerDrawable) item.getIcon();

        // Update LayerDrawable's BadgeDrawable
        NotificationUtil.setBadgeCount(this, icon, 2);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_notification) {
            startActivity(new Intent(getApplicationContext(), NotificationActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_my_profile) {
            // Handle the camera action
            startActivity(new Intent(getApplicationContext(), MyProfileActivity.class));
        } else if (id == R.id.nav_tread_alert) {
            startActivity(new Intent(getApplicationContext(), TradeAlertDateActivity.class));
        } else if (id == R.id.nav_tread_history) {
            startActivity(new Intent(getApplicationContext(), TradeHistoryDateActivity.class));
        } else if (id == R.id.nav_change_pass) {
            startActivity(new Intent(getApplicationContext(), ResetPassActivity.class));
        } else if (id == R.id.nav_demo) {
            startActivity(new Intent(getApplicationContext(), DemoActivity.class));
        } else if (id == R.id.nav_log_out) {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        } else if (id == R.id.nav_news) {
            startActivity(new Intent(getApplicationContext(), NewsActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
