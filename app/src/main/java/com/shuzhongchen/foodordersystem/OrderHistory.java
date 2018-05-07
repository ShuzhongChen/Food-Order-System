package com.shuzhongchen.foodordersystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OrderHistory extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    FirebaseAuth firebaseAuth;

   // FirebaseRecyclerAdapter<T, OrderViewHolder> adapter;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        firebaseAuth = FirebaseAuth.getInstance();

        String uid = firebaseAuth.getCurrentUser().getUid();

        database = FirebaseDatabase.getInstance();

        recyclerView = findViewById(R.id.listOrders);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //loadOrder(uid);


    }

//    private void loadOrder(String uid) {
//
//        adapter = new FirebaseRecyclerAdapter<T, OrderViewHolder>() {
//            @Override
//            protected void populateViewHolder(OrderViewHolder viewHolder, T model, int position) {
//
//            }
//        };
//    }
}
