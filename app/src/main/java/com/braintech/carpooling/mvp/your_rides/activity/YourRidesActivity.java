package com.braintech.carpooling.mvp.your_rides.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;


import com.braintech.carpooling.R;
import com.braintech.carpooling.common.helpers.LoginManagerSession;
import com.braintech.carpooling.mvp.splash_login.SplashLoginActivity;
import com.braintech.carpooling.mvp.your_rides.fragments.DashboardFragment;
import com.braintech.carpooling.mvp.your_rides.fragments.HistoryFragment;
import com.braintech.carpooling.mvp.your_rides.fragments.InboxFragment;
import com.braintech.carpooling.mvp.your_rides.fragments.ProfileFragment;

import butterknife.ButterKnife;

public class YourRidesActivity extends AppCompatActivity {


    FragmentManager fragmentManager;

    private final String TAG_DASHBOARD = "dashboard";
    private final String TAG_HISTORY = "history";
    private final String TAG_MY_PROFILe = "myProfile";
    private final String TAG_INBOX = "inbox";

    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_rides);

        ButterKnife.bind(this);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        addFragment();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
        if(fragmentManager.getBackStackEntryCount() == 1){
            if (doubleBackToExitPressedOnce) {
                finishAffinity();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, getString(R.string.title_back_again_to_exit), Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000);
        }else {
            super.onBackPressed();
        }
    }




    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_dashboard:
                    DashboardFragment dashboardFragment = new DashboardFragment();
                    replaceFragment(dashboardFragment,TAG_DASHBOARD);
                    return true;
                case R.id.navigation_history:
                    HistoryFragment historyFragment = new HistoryFragment();
                    replaceFragment(historyFragment,TAG_HISTORY);
                    return true;
                case R.id.navigation_inbox:
                    InboxFragment inboxFragment = new InboxFragment();
                    replaceFragment(inboxFragment,TAG_INBOX);
                    return true;
                case R.id.navigation_profile:
                    ProfileFragment profileFragment = new ProfileFragment();
                    replaceFragment(profileFragment,TAG_MY_PROFILe);
                    LoginManagerSession loginManagerSession = new LoginManagerSession();
                    loginManagerSession.logoutUser();
                    Intent intent = new Intent(YourRidesActivity.this, SplashLoginActivity.class);
                    startActivity(intent);
                    return true;
            }
            return false;
        }
    };

    /*private void addFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }*/

    private void addFragment() {
        DashboardFragment dashboardFragment = new DashboardFragment();
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().addToBackStack(TAG_DASHBOARD);
        fragmentTransaction.add(R.id.frame_container, dashboardFragment, TAG_DASHBOARD);
        fragmentTransaction.commitAllowingStateLoss();
    }

    private void replaceFragment(Fragment fragment, String tag) {
        /*fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment, tag).addToBackStack(tag);
        fragmentTransaction.commitAllowingStateLoss();*/
        int index = getSupportFragmentManager().getBackStackEntryCount() - 1;
        FragmentManager.BackStackEntry backEntry = getSupportFragmentManager().getBackStackEntryAt(index);
        String tagFragment = backEntry.getName();

        if(!tag.equals(tagFragment)){
            fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().addToBackStack(tag);
            fragmentTransaction.add(R.id.frame_container,fragment, tag);
            fragmentTransaction.commitAllowingStateLoss();
        }
    }
}
