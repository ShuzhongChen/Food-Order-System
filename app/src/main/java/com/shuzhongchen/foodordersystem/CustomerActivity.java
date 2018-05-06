package com.shuzhongchen.foodordersystem;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.shuzhongchen.foodordersystem.view.base.BaseFragment;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

/**
 * Created by shuzhongchen on 4/30/18.
 */

public class CustomerActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();




        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        mDrawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
            new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem item) {
                    if (item.isChecked()) {
                        mDrawerLayout.closeDrawers();
                        return true;
                    }

                    // Add code here to update the UI based on the item selected
                    // For example, swap UI fragments here

                    Fragment fragment = null;
                    switch (item.getItemId()) {
                        case R.id.nav_order:
                            fragment = BaseFragment.newInstance();
                            setTitle(R.string.order);
                            break;
                        case R.id.nav_history:
                            fragment = BaseFragment.newInstance();
                            setTitle(R.string.history);
                            break;
                        case R.id.nav_logout:
                            fragment = BaseFragment.newInstance();
                            setTitle(R.string.logout);
                            break;
                    }

                    mDrawerLayout.closeDrawers();

                    if (fragment != null) {
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container, fragment)
                                .commit();
                        return true;
                    }

                    return false;
                }
            }
        );

        mDrawerLayout.addDrawerListener(
                new DrawerLayout.DrawerListener() {
                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {
                        // Respond when the drawer's position changes
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        // Respond when the drawer is opened
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        // Respond when the drawer is closed
                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {
                        // Respond when the drawer motion state changes
                    }
                }
        );
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
