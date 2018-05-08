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
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shuzhongchen.foodordersystem.R;
import com.shuzhongchen.foodordersystem.activities.CustomerActivity;
import com.shuzhongchen.foodordersystem.helper.FragmentCommunication;
import com.shuzhongchen.foodordersystem.holders.MenuViewHolder;
import com.shuzhongchen.foodordersystem.holders.ShotViewHolder;
import com.shuzhongchen.foodordersystem.models.FoodInOrder;
import com.shuzhongchen.foodordersystem.models.Shot;
import com.shuzhongchen.foodordersystem.models.Menu;
import com.shuzhongchen.foodordersystem.view.base.SpaceItemDecoration;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * The child fragment is no different than any other fragment other than it is now being maintained by
 * a child FragmentManager.
 */
public class ShotListFragment extends Fragment {

    String[] category = new String[]{"drink", "appetizer", "main course", "desert"};

    FirebaseDatabase firebaseDatabase;
    DatabaseReference menuDB;

    FirebaseRecyclerAdapter<Menu, ShotViewHolder> adapter;
    ArrayList<FoodInOrder> foodList;

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
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        listType = getArguments().getInt(POSITION_KEY);

        firebaseDatabase = FirebaseDatabase.getInstance();
        menuDB = firebaseDatabase.getReference("menu");

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new SpaceItemDecoration(
                getResources().getDimensionPixelSize(R.dimen.spacing_medium)));

        CustomerActivity activity = (CustomerActivity) getActivity();
        foodList = new ArrayList<>(activity.getFoodList());

        loadAllMenu(listType);

    }

    private void loadAllMenu(final int listType) {

        FirebaseRecyclerOptions<Menu> allMenu = new FirebaseRecyclerOptions.Builder<Menu>()
                .setQuery(menuDB.orderByChild("category").equalTo(category[listType]), Menu.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<Menu, ShotViewHolder>(allMenu) {
            @NonNull
            @Override
            public ShotViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemview = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_iterm_shot, parent, false);
                return new ShotViewHolder(itemview);
            }

            @Override
            protected void onBindViewHolder(@NonNull final ShotViewHolder holder, final int position, @NonNull final Menu model) {

                final String name = model.getName();
                final int price = model.getUnitprice();
                final String id = model.getUUID();
                final int preptime = model.getPreptime();
                holder.price.setText(price + "");
                holder.title.setText(name);
                Picasso.get().load(model.getImage())
                        .into(holder.image);

                boolean containsFood = false;

                for (int i = 0; i < foodList.size(); i++) {
                    if (foodList.get(i).name.equals(name)) {
                        containsFood = true;
                        break;
                    }
                }

                if (containsFood) {
                    holder.btn.setImageResource(R.drawable.ic_check_black_24dp);
                } else {
                    holder.btn.setImageResource(R.drawable.ic_add_black_24dp);
                }
                holder.btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean containsFood = false;
                        int index = 0;
                        for (int i = 0; i < foodList.size(); i++) {
                            if (foodList.get(i).name.equals(name)) {
                                containsFood = true;
                                index = i;
                                break;
                            }
                        }
                        if (containsFood) {
                            foodList.remove(index);
                            ((FragmentCommunication) getActivity()).passIndex(foodList);
                            holder.btn.setImageResource(R.drawable.ic_add_black_24dp);
                        } else {
                            foodList.add(new FoodInOrder(id, name, price, 1, preptime));
                            ((FragmentCommunication) getActivity()).passIndex(foodList);
                            holder.btn.setImageResource(R.drawable.ic_check_black_24dp);
                        }
                    }
                });
            }
        };

        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }
}