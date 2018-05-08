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
import com.shuzhongchen.foodordersystem.holders.MenuOrderViewHolder;
import com.shuzhongchen.foodordersystem.holders.ShotViewHolder;
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

    FirebaseDatabase firebaseDatabase;
    DatabaseReference menuDB;

    FirebaseRecyclerAdapter<Menu, MenuOrderViewHolder> adapter;

    ArrayList<String> foodList;

    private int listType;
    public static final String POSITION_KEY = "FragmentPositionKey";

    public static CheckOutFragment newInstance(ArrayList<String> list) {
        Bundle args = new Bundle();
        args.putStringArrayList("food_list", list);
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

        foodList = new ArrayList<>(getArguments().getStringArrayList("food_list"));
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        firebaseDatabase = FirebaseDatabase.getInstance();
        menuDB = firebaseDatabase.getReference("menu");

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new SpaceItemDecoration(
                getResources().getDimensionPixelSize(R.dimen.spacing_medium)));

        loadAllMenu();
    }


    private void loadAllMenu() {

        FirebaseRecyclerOptions<Menu> allMenu = new FirebaseRecyclerOptions.Builder<Menu>()
                .setQuery(menuDB, Menu.class)
                .build();

        for (int i = 0; i < foodList.size(); i++) {
            Log.d("Shuzhong debug foodlist", foodList.get(i));
        }

        adapter = new FirebaseRecyclerAdapter<Menu, MenuOrderViewHolder>(allMenu) {
            @NonNull
            @Override
            public MenuOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemview = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.shopping_cart_item, parent, false);
                return new MenuOrderViewHolder(itemview);
            }

            @Override
            protected void onBindViewHolder(@NonNull MenuOrderViewHolder holder, final int position, @NonNull final Menu model) {
                if (foodList.contains(model.getName())) {
                    holder.order_food_price.setText(model.getUnitprice() + "");
                    holder.order_food_name.setText(model.getName());

                } else {
                    return;
                }
            }
        };

        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }


}
