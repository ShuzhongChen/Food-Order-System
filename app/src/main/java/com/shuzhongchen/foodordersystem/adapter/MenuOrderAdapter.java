package com.shuzhongchen.foodordersystem.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shuzhongchen.foodordersystem.R;
import com.shuzhongchen.foodordersystem.holders.MenuOrderViewHolder;
import com.shuzhongchen.foodordersystem.models.FoodInOrder;
import com.shuzhongchen.foodordersystem.models.QuantityPicker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shuzhongchen on 5/8/18.
 */

public class MenuOrderAdapter extends RecyclerView.Adapter {

    private List<FoodInOrder> data;

    public MenuOrderAdapter(@NonNull List<FoodInOrder> data) {
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shopping_cart_item, parent, false);
        return new MenuOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        FoodInOrder fo = data.get(position);
        ((MenuOrderViewHolder) holder).order_food_name.setText(fo.name);
        ((MenuOrderViewHolder) holder).order_food_price.setText("" + fo.price);
        ((MenuOrderViewHolder) holder).num = fo.num;
        ((MenuOrderViewHolder) holder).id = fo.id;
        ((MenuOrderViewHolder) holder).quantityPicker.setQuantitySelected(fo.num);


        /*((MenuOrderViewHolder) holder).quantityPicker.setOnQuantityChangeListener(new QuantityPicker.OnQuantityChangeListener() {
            @Override
            public void onValueChanged(int quantity) {

            }
        });*/
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
