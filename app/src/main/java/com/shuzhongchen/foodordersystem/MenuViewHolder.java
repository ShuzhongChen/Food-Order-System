package com.shuzhongchen.foodordersystem;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import info.hoang8f.widget.FButton;

/**
 * Created by jinchengcheng on 5/1/18.
 */

public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView nameTV;
    public TextView categoryTV;
    public TextView caloriesTV;
    public TextView UnitPriceTV;
    public TextView PrepTimeTV;

    public ImageButton imageButton;
    public Button editButton;
    public Button removeButton;



    //private ItemClickListener itemClickListener;

    public MenuViewHolder(View view) {
        super(view);

        nameTV = (TextView)view.findViewById(R.id.menu_name_textView);
        categoryTV = (TextView)view.findViewById(R.id.menu_category_textview);
        caloriesTV = (TextView) view.findViewById(R.id.menu_calories_textView);
        UnitPriceTV = (TextView) view.findViewById(R.id.menu_unit_price_textView);
        PrepTimeTV = (TextView) view.findViewById(R.id.menu_preptime_textView);
        imageButton = (ImageButton) view.findViewById(R.id.menu_image);

        editButton = (FButton)view.findViewById(R.id.edit_fbutton);
        removeButton = (FButton) view.findViewById(R.id.remove_fbutton);
    }

    @Override
    public void onClick(View view) {

    }
}
