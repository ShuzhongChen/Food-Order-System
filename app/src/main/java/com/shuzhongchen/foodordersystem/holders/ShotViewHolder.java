package com.shuzhongchen.foodordersystem.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.shuzhongchen.foodordersystem.R;

/**
 * Created by shuzhongchen on 5/6/18.
 */

public class ShotViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

    public TextView price;
    public TextView title;

    public ImageButton image;
    public ImageButton btn;

    public Boolean isAdd;

    public ShotViewHolder(View view) {
        super(view);
        isAdd = true;
        price = (TextView) view.findViewById(R.id.customer_menu_price);
        title = (TextView) view.findViewById(R.id.customer_menu_title);
        image = (ImageButton) view.findViewById(R.id.customer_menu_image);
        btn = (ImageButton) view.findViewById(R.id.customer_menu_btn);
    }

    @Override
    public void onClick(View view) {
        
    }
}
