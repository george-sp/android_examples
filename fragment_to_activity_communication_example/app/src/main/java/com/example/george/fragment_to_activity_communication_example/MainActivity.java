package com.example.george.fragment_to_activity_communication_example;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements FragmentToActivityInterface {

    private final static String LOG_TAG = MainActivity.class.getSimpleName();
    private CustomFragmentStatePagerAdapter mPagerAdapter;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ArrayList<String> tabs = new ArrayList<>();
        tabs.add("Tab A");
        tabs.add("Tab B");
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new CustomFragmentStatePagerAdapter(getSupportFragmentManager(), tabs);
        viewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            int position = mTabLayout.getSelectedTabPosition();
            Fragment fragment = mPagerAdapter.getFragment(mTabLayout.getSelectedTabPosition());
            if (fragment != null) {
                switch (position) {
                    case 0:
                        ((TabFragmentA) fragment).onRefresh();
                        break;
                    case 1:
                        ((TabFragmentB) fragment).onRefresh();
                        break;
                }
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void communicateToFragmentB() {
        TabFragmentB fragment = (TabFragmentB) mPagerAdapter.getFragment(1);
        if (fragment != null) {
            fragment.fragmentCommunication();
        } else {
            Log.i(LOG_TAG, "Fragment 2 is not initialized");
        }
    }

}