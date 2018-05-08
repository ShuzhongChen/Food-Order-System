package com.shuzhongchen.foodordersystem.view.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shuzhongchen.foodordersystem.R;
import com.shuzhongchen.foodordersystem.adapter.MenuOrderAdapter;
import com.shuzhongchen.foodordersystem.holders.MenuOrderViewHolder;
import com.shuzhongchen.foodordersystem.holders.ShotViewHolder;
import com.shuzhongchen.foodordersystem.models.FoodInOrder;
import com.shuzhongchen.foodordersystem.models.Menu;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by shuzhongchen on 5/8/18.
 */

public class CheckOutFragment extends Fragment {
    @BindView(R.id.order_recycler_view)
    RecyclerView recyclerView;

    ArrayList<FoodInOrder> foodList;

    public static CheckOutFragment newInstance(ArrayList<FoodInOrder> list) {
        Bundle args = new Bundle();
        args.putParcelableArrayList("food_list", list);
        CheckOutFragment fragment = new CheckOutFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_recycler_view, container, false);
        ButterKnife.bind(this, view);

        foodList = getArguments().getParcelableArrayList("food_list");
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new MenuOrderAdapter(foodList));

    }


    private void loadAllMenu() {

    }


}
