package com.vibeosys.tradenow;

import android.content.Intent;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.ActionMenuItem;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.TextView;

import com.vibeosys.tradenow.activities.BaseActivity;
import com.vibeosys.tradenow.activities.DemoActivity;
import com.vibeosys.tradenow.activities.DynamicPageActivity;
import com.vibeosys.tradenow.activities.LoginActivity;
import com.vibeosys.tradenow.activities.MyProfileActivity;
import com.vibeosys.tradenow.activities.NewsActivity;
import com.vibeosys.tradenow.activities.NotificationActivity;
import com.vibeosys.tradenow.activities.ResetPassActivity;
import com.vibeosys.tradenow.activities.TradeAlertDateActivity;
import com.vibeosys.tradenow.activities.TradeHistoryDateActivity;
import com.vibeosys.tradenow.services.PageSyncService;
import com.vibeosys.tradenow.services.SignalSyncService;
import com.vibeosys.tradenow.services.TradeBackupSyncService;
import com.vibeosys.tradenow.utils.NotificationUtil;
import com.vibeosys.tradenow.utils.TradeBackupSyncManager;
import com.vibeosys.tradenow.utils.UserAuth;

import java.util.ArrayList;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private View mainActivityView;
    private Intent syncServiceIntent;
    private Intent syncHistoryIntent;
    private Intent syncPageIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        syncServiceIntent = new Intent(Intent.ACTION_SYNC, null, this, SignalSyncService.class);
        syncHistoryIntent = new Intent(Intent.ACTION_SYNC, null, this, TradeBackupSyncService.class);
        syncPageIntent = new Intent(Intent.ACTION_SYNC, null, this, PageSyncService.class);
        if (!UserAuth.isUserLoggedIn()) {
            callToLogOut();
        } else {
            mainActivityView = findViewById(R.id.mainActivityView);
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

       /* if (!isMyServiceRunning(SignalSyncService.class))*/

            View headerView = navigationView.getHeaderView(0);
            TextView txtUserName = (TextView) headerView.findViewById(R.id.txtUserName);
            txtUserName.setText(mSessionManager.getUserFullName());
            TextView txtEmail = (TextView) headerView.findViewById(R.id.txtEmail);
            txtEmail.setText(mSessionManager.getUserEmailId());

            if (mSessionManager.getSubId() > 0) {
                navigationView.getMenu().clear(); //clear old inflated items.
                navigationView.inflateMenu(R.menu.activity_main_drawer);// drawer for subscribers

                startService(syncServiceIntent);
                startService(syncHistoryIntent);
                startService(syncPageIntent);
                addNavigationPages(navigationView);
            } else {
                navigationView.getMenu().clear(); //clear old inflated items.
                navigationView.inflateMenu(R.menu.activity_user_drawer);
            }
        }

    }

    private void addNavigationPages(NavigationView navigationView) {
        Menu menu = navigationView.getMenu();
        SubMenu subMenuPage = menu.addSubMenu("Pages");
        int groupId = subMenuPage.getItem().getGroupId();
        ArrayList<String> pages = mDbRepository.getMobilePageList();
        for (int i = 0; i < pages.size(); i++) {
            subMenuPage.add(pages.get(i)).setIcon(getResources().getDrawable(R.drawable.ic_description_black_24dp));
        }
        //navigationView.notify();
    }


    @Override
    protected View getMainView() {
        return this.mainActivityView;
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
            if (isMyServiceRunning(SignalSyncService.class)) {
                try {
                    stopService(syncServiceIntent);
                    stopService(syncHistoryIntent);
                    stopService(syncPageIntent);
                } catch (NullPointerException e) {

                }

            }
            callToLogOut();
        } else if (id == R.id.nav_news) {
            startActivity(new Intent(getApplicationContext(), NewsActivity.class));
        } else {
            String pageTitle = item.getTitle().toString();
            Intent iDynamic = new Intent(getApplicationContext(), DynamicPageActivity.class);
            iDynamic.putExtra("pageTitle", pageTitle);
            startActivity(iDynamic);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void callToLogOut() {

        mDbRepository.deleteAllData();
        UserAuth.CleanAuthenticationInfo();
        Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
        loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
        finish();
    }
}
