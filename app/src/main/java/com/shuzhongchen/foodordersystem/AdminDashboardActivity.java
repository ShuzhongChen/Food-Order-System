package com.shuzhongchen.foodordersystem;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

/**
 * Created by jinchengcheng on 4/27/18.
 */

public class AdminDashboardActivity extends AppCompatActivity {

    private ImageButton addImage;
    private ListView listView;
    String[] menuName = {"test1", "test2", "test3", "test4"};
    String[] menuCategory = {"category1", "category2", "category3", "category4"};
    int[] menuCalories = {1,2,3,4};
    int[] menuUnitPrice = {1,2,3,4};
    int[] menuPrepTime = {1,2,3,4};

    private Activity content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        initial();

        listView = (ListView) findViewById(R.id.MenuListView);
        CustomListView customListView = new CustomListView(this, menuName, menuCategory, menuUnitPrice, menuCalories, menuPrepTime);
        listView.setAdapter(customListView);


//        addImage.setOnClickListener ((new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FragmentManager manager = getFragmentManager();
//                FragmentTransaction transaction = manager.beginTransaction();
//                transaction.replace(R.id.MainContainer,new addMenuItemFragment());
//                System.out.print("executed replace function");
//                transaction.addToBackStack(null);
//                transaction.commit();
//            }
//        }));


    }

    private void initial() {
        //addImage = (ImageButton) findViewById(R.id.addImageButton);
    }

}
