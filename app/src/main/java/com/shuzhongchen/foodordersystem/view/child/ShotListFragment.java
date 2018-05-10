package com.shuzhongchen.foodordersystem.view.child;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.reflect.TypeToken;
import com.shuzhongchen.foodordersystem.R;
import com.shuzhongchen.foodordersystem.activities.CustomerActivity;
import com.shuzhongchen.foodordersystem.adapter.MenuOrderAdapter;
import com.shuzhongchen.foodordersystem.adapter.ShotListAdapter;
import com.shuzhongchen.foodordersystem.helper.FragmentCommunication;
import com.shuzhongchen.foodordersystem.helper.ModelUtils;
import com.shuzhongchen.foodordersystem.holders.MenuViewHolder;
import com.shuzhongchen.foodordersystem.holders.ShotViewHolder;
import com.shuzhongchen.foodordersystem.models.FoodInOrder;
import com.shuzhongchen.foodordersystem.models.Order;
import com.shuzhongchen.foodordersystem.models.Shot;
import com.shuzhongchen.foodordersystem.models.Menu;
import com.shuzhongchen.foodordersystem.view.base.SpaceItemDecoration;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * The child fragment is no different than any other fragment other than it is now being maintained by
 * a child FragmentManager.
 */
public class ShotListFragment extends Fragment {

    String[] category = new String[]{"appetizer", "dessert", "drink", "main course"};


    private String MODEL_FOODLIST = "food_list";
    private int pos = 0;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;


    private int listType;
    public static final String POSITION_KEY = "FragmentPositionKey";

    public static ShotListFragment newInstance(Bundle args) {
        ShotListFragment fragment = new ShotListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_swipe_recycler_view, container, false);
        ButterKnife.bind(this, view);
        Spinner spinner = (Spinner) view.findViewById(R.id.planets_spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Log.d("Shuzhong debug "," yes");
                pos = parentView.getSelectedItemPosition();
                listType = getArguments().getInt(POSITION_KEY);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                loadAllMenu(listType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        listType = getArguments().getInt(POSITION_KEY);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new SpaceItemDecoration(
                getResources().getDimensionPixelSize(R.dimen.spacing_medium)));

        CustomerActivity activity = (CustomerActivity) getActivity();

        loadAllMenu(listType);

    }

    private void loadAllMenu(final int listType) {

        List<Menu> menuList = new ArrayList<>(ModelUtils.read(getContext(),
                "menu_list",
                new TypeToken<List<Menu>>(){}));
        List<Menu> newList = new ArrayList<>();
        for (int i = 0; i < menuList.size(); i++) {
            if (menuList.get(i).getCategory().equals(category[listType])) {
                newList.add(menuList.get(i));
            }
        }

        sortList(newList, pos);
        recyclerView.setAdapter(new ShotListAdapter(newList));
    }

    private void sortList(List<Menu> newList, int p) {
        switch (p) {
            case 0:
                Collections.sort(newList, new Comparator<Menu>() {
                    @Override
                    public int compare(Menu m1, Menu m2) {
                        return m1.getUnitprice() - m2.getUnitprice();
                    }
                });
                break;

            case 1:
                Collections.sort(newList, new Comparator<Menu>() {
                    @Override
                    public int compare(Menu m1, Menu m2) {
                        return m1.getName().compareTo(m2.getName());
                    }
                });
                break;

            case 2:
                Collections.sort(newList, new Comparator<Menu>() {
                    @Override
                    public int compare(Menu m1, Menu m2) {
                        return m1.getOrdertimes() - m2.getOrdertimes();
                    }
                });
                break;
        }

    }
}