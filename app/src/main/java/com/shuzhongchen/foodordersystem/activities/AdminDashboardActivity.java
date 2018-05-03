package com.shuzhongchen.foodordersystem.activities;

import android.app.Activity;
import android.app.AlertDialog;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.shuzhongchen.foodordersystem.holders.CustomListView;
import com.shuzhongchen.foodordersystem.R;
import com.shuzhongchen.foodordersystem.holders.MenuViewHolder;
import com.shuzhongchen.foodordersystem.models.Menu;


/**
 * Created by jinchengcheng on 4/27/18.
 */

public class AdminDashboardActivity extends AppCompatActivity {

    private ImageButton addImage;

    private Activity content;

    private FloatingActionButton floatingActionButton;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference menuDB;
    static int id;

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Menu, MenuViewHolder> adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        floatingActionButton = (FloatingActionButton) findViewById(R.id.add_fab);
        recyclerView = (RecyclerView) findViewById(R.id.recycle_menu);
        layoutManager = new LinearLayoutManager(this);

        firebaseDatabase = FirebaseDatabase.getInstance();
        menuDB = firebaseDatabase.getReference("menu");


        loadAllMenu();

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

        id = 1;
        FirebaseRecyclerOptions<Menu> allMenu = new FirebaseRecyclerOptions.Builder<Menu>()
                .setQuery(menuDB, Menu.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<Menu, MenuViewHolder>(allMenu) {
            @NonNull
            @Override
            public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemview = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.menu_dispaly, parent, false);
                return new MenuViewHolder(itemview);
            }

            @Override
            protected void onBindViewHolder(@NonNull MenuViewHolder holder, final int position, @NonNull final Menu model) {

                holder.idTV.setText("" + id++);
                holder.nameTV.setText(model.getName());
                holder.categoryTV.setText(model.getCategory());
                holder.caloriesTV.setText("" + model.getCalories());
                holder.UnitPriceTV.setText("" + model.getUnitPrice());
                holder.PrepTimeTV.setText("" + model.getPrepTime());

                holder.removeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        removeMenu(adapter.getRef(position).getKey());
                    }
                });
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

                // to do : update information to firebase
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
        menuDB.child(key)
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
        id = 1;
        adapter.notifyDataSetChanged();
    }


    private void showCreateMenuLayout() {

        AlertDialog.Builder create_menu_dialog = new AlertDialog.Builder(AdminDashboardActivity.this);
        create_menu_dialog.setTitle("Create Menu");

        LayoutInflater layoutInflater = this.getLayoutInflater();
        final View CreateView = layoutInflater.inflate(R.layout.activity_menu_detail,null);

        create_menu_dialog.setView(CreateView);
        create_menu_dialog.setIcon(R.drawable.ic_restaurant_menu_black_24dp);

        create_menu_dialog.setPositiveButton("CREATE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                EditText createName = (EditText)CreateView.findViewById(R.id.MenuNameTextView);
                EditText createCalories = (EditText)CreateView.findViewById(R.id.caloriesTextView);
                EditText createUnitPrice = (EditText)CreateView.findViewById(R.id.UnitPriceTextView);
                EditText createPrepTime = (EditText)CreateView.findViewById(R.id.prepTimeTextView);

                Menu menu = new Menu();
                menu.setName(createName.getText().toString())
                        .setImage("test")
                        .setCategory("test")
                        .setCalories(Integer.parseInt(createCalories.getText().toString()))
                        .setUnitPrice(Integer.parseInt(createUnitPrice.getText().toString()))
                        .setPrepTime(Integer.parseInt(createPrepTime.getText().toString()));

                menuDB.child(String.valueOf(id))
                        .setValue(menu)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(AdminDashboardActivity.this, "menu created!", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AdminDashboardActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                dialogInterface.dismiss();
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
