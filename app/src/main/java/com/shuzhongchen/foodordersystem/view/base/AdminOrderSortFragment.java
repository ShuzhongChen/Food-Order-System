package com.shuzhongchen.foodordersystem.view.base;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.shuzhongchen.foodordersystem.OrderViewHolder;
import com.shuzhongchen.foodordersystem.R;
import com.shuzhongchen.foodordersystem.activities.MenuSortActivity;
import com.shuzhongchen.foodordersystem.adapter.AdminOrderAdapter;
import com.shuzhongchen.foodordersystem.holders.AdminOrderHolder;
import com.shuzhongchen.foodordersystem.models.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jinchengcheng on 5/15/18.
 */

public class AdminOrderSortFragment extends Fragment {

    public RecyclerView OrderRecyclerView;
    public RecyclerView.LayoutManager OrderLayoutManager;

    private static List<Order> listOfOrder;

    private static Context fragmentContext;

    public static AdminOrderSortFragment newInstance(Context context, List<Order> choosedOrders) {
        Bundle args = new Bundle();
        AdminOrderSortFragment fragment = new AdminOrderSortFragment();
        fragment.setArguments(args);
        fragmentContext = context;
        listOfOrder = choosedOrders;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewRoot = inflater.inflate(R.layout.admin_order_fragment, container, false);

        OrderRecyclerView = (RecyclerView) viewRoot.findViewById(R.id.AdminListOrders);
        OrderLayoutManager = new LinearLayoutManager(fragmentContext);
        OrderRecyclerView.setHasFixedSize(true);
        OrderRecyclerView.setLayoutManager(OrderLayoutManager);
        loadOrder(listOfOrder);
        return viewRoot;
    }

    private void loadOrder(List<Order> choosedOrder) {

        RecyclerView.Adapter adapter = new AdminOrderAdapter( fragmentContext, choosedOrder);
        OrderRecyclerView.setAdapter(adapter);
    }

}
