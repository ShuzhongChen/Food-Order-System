package com.shuzhongchen.foodordersystem;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import info.hoang8f.widget.FButton;


/**
 * Created by jinchengcheng on 4/27/18.
 */

public class AdminDashboardActivity extends AppCompatActivity {

    private ImageButton addImage;
    String[] menuName = {"test1", "test2", "test3", "test4"};
    String[] menuCategory = {"category1", "category2", "category3", "category4"};
    int[] menuCalories = {1,2,3,4};
    int[] menuUnitPrice = {1,2,3,4};
    int[] menuPrepTime = {1,2,3,4};

    private Activity content;


    FloatingActionButton floatingActionButton;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference menu;

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Menu, MenuViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        initial();

        CustomListView customListView = new CustomListView(this, menuName, menuCategory, menuUnitPrice, menuCalories, menuPrepTime);

        floatingActionButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
               showCreateMenuLayout();
            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

    }

    private void loadAllMenu() {
        final FirebaseRecyclerOptions<Menu> allMenu = new FirebaseRecyclerOptions.Builder<Menu>()
                .setQuery(menu, Menu.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<Menu, MenuViewHolder>(allMenu) {
            @Override
            protected void onBindViewHolder(@NonNull MenuViewHolder holder, final int position, @NonNull final Menu model) {
                holder.nameTV.setText(model.getName());
                holder.categoryTV.setText(model.getCategory());
                holder.caloriesTV.setText(model.getCalories());
                holder.UnitPriceTV.setText(model.getUnitPrice());
                holder.PrepTimeTV.setText(model.getPrepTime());

                holder.editButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showEditDialog(adapter.getRef(position).getKey(), model);
                    }
                });

                holder.removeButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        removeMenu(adapter.getRef(position).getKey());
                    }
                });
            }

            @NonNull
            @Override
            public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemview = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.activity_admin, parent, false);
                return new MenuViewHolder(itemview);
            }
        };

        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }


    private void showEditDialog(String key, Menu model) {
        AlertDialog.Builder create_menu_dialog = new AlertDialog.Builder(AdminDashboardActivity.this);
        create_menu_dialog.setTitle("Update Menu");

        //update everyfield from this input page
        LayoutInflater layoutInflater = this.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.activity_menu_detail,null);

        create_menu_dialog.setView(view);

        create_menu_dialog.setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

                Menu menu = new Menu();
                menu.setName("testFunction")
                        .setCalories(100)
                        .setCategory("testFunction")
                        .setPrepTime(100)
                        .setUnitPrice(100);
            }
        });

        create_menu_dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        create_menu_dialog.show();
    }


    private void removeMenu(String key) {
        menu.child(key)
                .removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AdminDashboardActivity.this, "Remove Succeed", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AdminDashboardActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        adapter.notifyDataSetChanged();

    }


    private void initial() {
        floatingActionButton = (FloatingActionButton) findViewById(R.id.add_fab);
        recyclerView = (RecyclerView) findViewById(R.id.recycle_menu);
        layoutManager = new LinearLayoutManager(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        menu = firebaseDatabase.getReference("menu");
    }

    private void showCreateMenuLayout() {
        AlertDialog.Builder create_menu_dialog = new AlertDialog.Builder(AdminDashboardActivity.this);
        create_menu_dialog.setTitle("Create Menu");

        LayoutInflater layoutInflater = this.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.activity_menu_detail,null);

        create_menu_dialog.setView(view);

        create_menu_dialog.setPositiveButton("CREATE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

                Menu menu = new Menu();
                menu.setName("testFunction")
                        .setCalories(100)
                        .setCategory("testFunction")
                        .setPrepTime(100)
                        .setUnitPrice(100);
            }
        });

        create_menu_dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        create_menu_dialog.show();
    }
}
