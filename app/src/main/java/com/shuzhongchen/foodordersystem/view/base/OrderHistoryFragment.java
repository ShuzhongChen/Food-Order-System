package com.shuzhongchen.foodordersystem.view.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.shuzhongchen.foodordersystem.OrderViewHolder;
import com.shuzhongchen.foodordersystem.R;
import com.shuzhongchen.foodordersystem.models.Order;

/**
 * Created by mengtongma on 5/9/18.
 */

public class OrderHistoryFragment extends Fragment {

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    FirebaseAuth firebaseAuth;
    DatabaseReference orders;

    FirebaseRecyclerAdapter<Order, OrderViewHolder> adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewRoot = inflater.inflate(R.layout.activity_order_history, container, false);

        firebaseAuth = FirebaseAuth.getInstance();

        String uid = firebaseAuth.getCurrentUser().getUid();

        database = FirebaseDatabase.getInstance();
        orders = database.getReference("Orders");

        recyclerView = viewRoot.findViewById(R.id.listOrders);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        loadOrder(uid);
        return viewRoot;
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
            protected void onBindViewHolder(@NonNull final OrderViewHolder holder, int position, @NonNull final Order model) {
                holder.txtOrderId.setText(adapter.getRef(position).getKey());
                final String orderStatus = model.getStatus();
                holder.txtOrderStatus.setText(orderStatus);
                holder.txtOrderCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (orderStatus.equals("fulfilled")) {
                            //Toast.makeText(getContext(), "Sorry, the order can't be canceled", Toast.LENGTH_SHORT).show();
                            Snackbar snackbar = Snackbar.make(getView(), "Sorry, the order can't be canceled!", Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                        else {
                            Snackbar snackbar = Snackbar.make(getView(), "Order canceled!", Snackbar.LENGTH_LONG);
                            snackbar.show();
                            holder.txtOrderStatus.setText("abandoned");
                        }


                    }
                });
            }


        };

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
