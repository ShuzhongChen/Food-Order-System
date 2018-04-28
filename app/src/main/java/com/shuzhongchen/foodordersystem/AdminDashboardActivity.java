package com.shuzhongchen.foodordersystem;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by jinchengcheng on 4/27/18.
 */

public class AdminDashboardActivity extends AppCompatActivity {

    private ImageView addImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        initial();

    }

    private void initial() {
        addImage = (ImageView) findViewById(R.id.addImageView);
    }

    public void OnItemAdded(View view) {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.MainContainer,new addMenuItemFragment());
        System.out.print("executed replace function");
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
