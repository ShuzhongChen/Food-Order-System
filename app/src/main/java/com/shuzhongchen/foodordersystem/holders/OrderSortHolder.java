package com.shuzhongchen.foodordersystem.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shuzhongchen.foodordersystem.R;
import com.shuzhongchen.foodordersystem.interfaces.ItemClickListener;

/**
 * Created by jinchengcheng on 5/19/18.
 */

public class OrderSortHolder extends RecyclerView.ViewHolder {

    public TextView popularityTV;
    public TextView categoryTV;
    public TextView caloriesTV;
    public TextView UnitPriceTV;
    public TextView PrepTimeTV;
    public TextView nameTV;

    public OrderSortHolder(View view) {
        super(view);
        popularityTV = (TextView) view.findViewById(R.id.popularity_sort_textview);
        nameTV = (TextView) view.findViewById(R.id.name_sort_textview);
        categoryTV = (TextView) view.findViewById(R.id.category_sort_textview);
        caloriesTV = (TextView) view.findViewById(R.id.calories_sort_textview);
        UnitPriceTV = (TextView) view.findViewById(R.id.unitprice_sort_textview);
        PrepTimeTV = (TextView) view.findViewById(R.id.preptime_sort_textview);

    }

}
