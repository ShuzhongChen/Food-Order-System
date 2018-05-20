package com.shuzhongchen.foodordersystem.view.child;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shuzhongchen.foodordersystem.R;
import com.shuzhongchen.foodordersystem.adapter.OrderSortAdapter;
import com.shuzhongchen.foodordersystem.models.Menu;
import com.shuzhongchen.foodordersystem.models.Order;
import com.shuzhongchen.foodordersystem.view.base.AdminPopularityFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by jinchengcheng on 5/19/18.
 */

public class AdminPopularityFragmentChild extends Fragment {

    private static ArrayList<Menu> menus;
    private static Context childContext;

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    public static AdminPopularityFragmentChild newInstance(Context context, ArrayList<Menu> menuList) {
        Bundle args = new Bundle();
        AdminPopularityFragmentChild fragment = new AdminPopularityFragmentChild();
        fragment.setArguments(args);
        childContext = context;
        menus = menuList;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewRoot = inflater.inflate(R.layout.admin_popularity_fragment_two, container, false);
        recyclerView = (RecyclerView) viewRoot.findViewById(R.id.AdminListPopularity);
        layoutManager = new LinearLayoutManager(childContext);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        loadMenu(menus);
        return viewRoot;
    }

    private void loadMenu(ArrayList<Menu> Menus) {

        ArrayList<Menu> newList = sortList(Menus);
        RecyclerView.Adapter adapter = new OrderSortAdapter( childContext, newList);
        recyclerView.setAdapter(adapter);
    }

    private ArrayList<Menu> sortList(ArrayList<Menu> newList) {
        Collections.sort(newList, new Comparator<Menu>() {
            @Override
            public int compare(Menu m1, Menu m2) {
                return m2.getOrdertimes() - m1.getOrdertimes();
            }
        });

        return newList;

    }
}
