package com.vibeosys.tradenow.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.vibeosys.tradenow.fragments.ClientUserLoginFragment;
import com.vibeosys.tradenow.fragments.NewUserLoginFragment;

/**
 * Created by akshay on 18-06-2016.
 */
public class LoginFragmentAdapter extends FragmentPagerAdapter {

    private int itemCount;

    public LoginFragmentAdapter(FragmentManager fm, int count) {
        super(fm);
        this.itemCount = count;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                NewUserLoginFragment newUser = new NewUserLoginFragment();
                return newUser;

            case 1:
                ClientUserLoginFragment clientUser = new ClientUserLoginFragment();
                return clientUser;
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return itemCount;
    }
}