package com.shuzhongchen.foodordersystem.view.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.shuzhongchen.foodordersystem.OrderViewHolder;
import com.shuzhongchen.foodordersystem.R;
import com.shuzhongchen.foodordersystem.activities.MenuSortActivity;
import com.shuzhongchen.foodordersystem.holders.AdminOrderHolder;
import com.shuzhongchen.foodordersystem.models.Order;

/**
 * Created by jinchengcheng on 5/15/18.
 */

public class AdminOrderSortFragment extends Fragment {

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference orders;

    FirebaseRecyclerAdapter<Order, AdminOrderHolder> adapter;


    public static AdminOrderSortFragment newInstance() {
        Bundle args = new Bundle();
        AdminOrderSortFragment fragment = new AdminOrderSortFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewRoot = inflater.inflate(R.layout.admin_order_fragment, container, false);

        System.out.println("admin order fragment called");

        database = FirebaseDatabase.getInstance();
        orders = database.getReference("Orders");

        recyclerView = viewRoot.findViewById(R.id.AdminListOrders);
        final FragmentActivity c = getActivity();
        layoutManager = new LinearLayoutManager(c);
        recyclerView.setLayoutManager(layoutManager);

        loadOrder();

        return viewRoot;
    }

    private void loadOrder() {
        System.out.println("load menu called: " + "\n");

        Query query = database
                .getReference()
                .child("Orders");

        System.out.println("query: " + query + "\n");

        FirebaseRecyclerOptions<Order> options =
                new FirebaseRecyclerOptions.Builder<Order>()
                        .setQuery(query, Order.class)
                        .build();

        System.out.println("orders:" + orders + "\n");

        adapter = new FirebaseRecyclerAdapter<Order, AdminOrderHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull AdminOrderHolder holder, int position, @NonNull Order model) {
                System.out.println("onBindViewholder clalled" + "\n");
                holder.TotalPrice.setText("test2");
                holder.Status.setText("test3");
                holder.PickupTime.setText("test4");
                holder.StartTime.setText("test5");
                holder.OrderId.setText("test6");
                holder.ReadyTime.setText("test7");
            }

            @NonNull
            @Override
            public AdminOrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.admin_order_display, parent, false);
                System.out.println("on create view holder called" + "\n");
                return new AdminOrderHolder(view);
            }
        };

        System.out.println("adapter snapshot" + adapter.getSnapshots() + "\n");
        System.out.println("adapter item counts:" + adapter.getItemCount() + "\n");
        recyclerView.setAdapter(adapter);

    }


}
