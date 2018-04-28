package com.shuzhongchen.foodordersystem;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

/**
 * Created by jinchengcheng on 4/27/18.
 */

public class AdminDashboardActivity extends AppCompatActivity {

    private ImageButton addImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        initial();

        addImage.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.MainContainer,new addMenuItemFragment());
                System.out.print("executed replace function");
                transaction.addToBackStack(null);
                transaction.commit();
            }
        }));

    }

    private void initial() {
        addImage = (ImageButton) findViewById(R.id.addImageButton);
    }

}
