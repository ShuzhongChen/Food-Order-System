package com.shuzhongchen.foodordersystem;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.shuzhongchen.foodordersystem.models.Order;

import java.util.Stack;

public class OrderHistory extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    private LinearLayoutManager mLayoutManager;

    FirebaseDatabase database;
    FirebaseAuth firebaseAuth;
    DatabaseReference orders;

    FirebaseRecyclerAdapter<Order, OrderViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        firebaseAuth = FirebaseAuth.getInstance();

        String uid = firebaseAuth.getCurrentUser().getUid();

        database = FirebaseDatabase.getInstance();
        orders = database.getReference("Orders");

        // Now set the layout manager and the adapter to the RecyclerView
        recyclerView = findViewById(R.id.listOrders);
        recyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        //mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(mLayoutManager);

        //recyclerView.setLayoutManager(layoutManager);

        loadOrder(uid);

    }

    private void loadOrder(String uid) {

        Query query = database
                .getReference()
                .child("Orders").orderByChild("uid").equalTo(uid)
                .limitToLast(50);

        FirebaseRecyclerOptions<Order> options =
                new FirebaseRecyclerOptions.Builder<Order>()
                        .setQuery(query, Order.class)
                        .build();

        adapter = new FirebaseRecyclerAdapter<Order, OrderViewHolder>(options) {

            @NonNull
            @Override
            public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.order_layout, parent, false);
                return new OrderViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull OrderViewHolder holder, int position, @NonNull Order model) {
                holder.txtOrderId.setText(adapter.getRef(position).getKey());
                holder.txtOrderStatus.setText(model.getStatus());
            }

        };

        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}
