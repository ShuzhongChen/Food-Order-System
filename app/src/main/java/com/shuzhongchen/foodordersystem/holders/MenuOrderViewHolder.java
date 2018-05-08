package com.shuzhongchen.foodordersystem.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.shuzhongchen.foodordersystem.R;

/**
 * Created by shuzhongchen on 5/8/18.
 */

public class MenuOrderViewHolder extends RecyclerView.ViewHolder {
    public TextView order_food_name;
    public TextView order_food_price;



    public MenuOrderViewHolder(View view) {
        super(view);
        order_food_name = (TextView) view.findViewById(R.id.order_food_name);
        order_food_price = (TextView) view.findViewById(R.id.order_food_price);
    }

}
