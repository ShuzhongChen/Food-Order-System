package com.shuzhongchen.foodordersystem.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shuzhongchen.foodordersystem.R;
import com.shuzhongchen.foodordersystem.holders.AdminOrderHolder;
import com.shuzhongchen.foodordersystem.holders.OrderSortHolder;
import com.shuzhongchen.foodordersystem.models.Menu;
import com.shuzhongchen.foodordersystem.models.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jinchengcheng on 5/19/18.
 */

public class OrderSortAdapter extends RecyclerView.Adapter {

    private ArrayList<Menu> data;

    private Context context;

    public OrderSortAdapter(Context context, @NonNull ArrayList<Menu> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.admin_sort_order, parent, false);
        return new OrderSortHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Menu model = data.get(position);
        ((OrderSortHolder)holder).popularityTV.setText("" + model.getOrdertimes());
        ((OrderSortHolder)holder).caloriesTV.setText("" + model.getCalories());
        ((OrderSortHolder)holder).categoryTV.setText("" + model.getCategory());
        ((OrderSortHolder)holder).nameTV.setText("" + model.getName());
        ((OrderSortHolder)holder).PrepTimeTV.setText("" + model.getPreptime());
        ((OrderSortHolder)holder).UnitPriceTV.setText("" + model.getUnitprice());
        System.out.println("on bind veiw holder called " + "\n");
        System.out.println("get order times: " + model.getOrdertimes());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
