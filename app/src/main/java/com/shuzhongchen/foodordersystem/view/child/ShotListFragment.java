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
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shuzhongchen.foodordersystem.R;
import com.shuzhongchen.foodordersystem.model.Shot;
import com.shuzhongchen.foodordersystem.view.base.SpaceItemDecoration;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * The child fragment is no different than any other fragment other than it is now being maintained by
 * a child FragmentManager.
 */
public class ShotListFragment extends Fragment {

    public static final int REQ_CODE_SHOT = 100;
    public static final String KEY_LIST_TYPE = "listType";
    public static final String KEY_BUCKET_ID = "bucketId";

    public static final int LIST_TYPE_POPULAR = 1;
    public static final int LIST_TYPE_LIKED = 2;
    public static final int LIST_TYPE_BUCKET = 3;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference menuDB;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    SwipeRefreshLayout swipeRefreshLayout;

    private ShotListAdapter adapter;

    private int listType;
    public static final String POSITION_KEY = "FragmentPositionKey";
    private int position;

    public static ShotListFragment newInstance(int listType) {
        Bundle args = new Bundle();
        args.putInt(KEY_LIST_TYPE, listType);

        ShotListFragment fragment = new ShotListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static ShotListFragment newBucketListInstance(@NonNull String bucketId) {
        Bundle args = new Bundle();
        args.putInt(KEY_LIST_TYPE, LIST_TYPE_BUCKET);
        args.putString(KEY_BUCKET_ID, bucketId);

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
        listType = getArguments().getInt(KEY_LIST_TYPE);

        swipeRefreshLayout.setEnabled(false);

        firebaseDatabase = FirebaseDatabase.getInstance();
        menuDB = firebaseDatabase.getReference("menu");


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new SpaceItemDecoration(
                getResources().getDimensionPixelSize(R.dimen.spacing_medium)));

        adapter = new ShotListAdapter(mockData());
        recyclerView.setAdapter(adapter);

    }

    @NonNull
    private List<Shot> mockData() {
        List<Shot> list = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            list.add(new Shot("todo " + i));
        }
        return list;
    }
}