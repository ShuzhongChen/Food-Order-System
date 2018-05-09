package com.shuzhongchen.foodordersystem.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.reflect.TypeToken;
import com.shuzhongchen.foodordersystem.OrderHistory;
import com.shuzhongchen.foodordersystem.R;
import com.shuzhongchen.foodordersystem.helper.FragmentCommunication;
import com.shuzhongchen.foodordersystem.helper.ModelUtils;
import com.shuzhongchen.foodordersystem.models.FoodInOrder;
import com.shuzhongchen.foodordersystem.view.base.BaseFragment;
import com.shuzhongchen.foodordersystem.view.base.CheckOutFragment;
import com.shuzhongchen.foodordersystem.view.base.OrderHistoryFragment;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

/**
 * Created by shuzhongchen on 4/30/18.
 */

public class CustomerActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private String MODEL_FOODLIST = "food_list";

    FirebaseAuth mAuth;

    ImageButton addToCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);



        Toolbar toolbar = findViewById(R.id.toolbar);

        List<FoodInOrder> foodList = ModelUtils.read(this,
                MODEL_FOODLIST,
                new TypeToken<List<FoodInOrder>>(){});
        List<FoodInOrder> newList = foodList == null ? new ArrayList<FoodInOrder>() : foodList;
        ModelUtils.save(this, MODEL_FOODLIST, newList);

        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();



        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        addToCart = findViewById(R.id.toolbar_add_to_cart);
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = CheckOutFragment.newInstance();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit();
                setTitle(R.string.history);
            }
        });

        mDrawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        View hView = navigationView.getHeaderView(0);
        TextView nav_user = hView.findViewById(R.id.headerTitle);
        nav_user.setText(mAuth.getCurrentUser().getDisplayName());

        //set base fragment as default
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.fragment_container, BaseFragment.newInstance());
        tx.commit();

        navigationView.setNavigationItemSelectedListener(
            new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem item) {
                    // Add code here to update the UI based on the item selected
                    // For example, swap UI fragments here

                    Fragment fragment = null;
                    switch (item.getItemId()) {
                        case R.id.nav_order:
                            fragment = BaseFragment.newInstance();
                            setTitle(R.string.order);
                            break;
                        case R.id.nav_history:
                            //fragment = BaseFragment.newInstance();
//                            Intent history = new Intent(getApplicationContext(), OrderHistory.class);
//                            startActivity(history);
                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.fragment_container, new OrderHistoryFragment())
                                    .addToBackStack(null)
                                    .commit();
                            setTitle(R.string.history);
                            break;
                        case R.id.nav_logout:
//                            fragment = BaseFragment.newInstance();
//                            setTitle(R.string.logout);
                            mAuth.signOut();
                            finish();
                            Intent backToHome = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(backToHome);
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
