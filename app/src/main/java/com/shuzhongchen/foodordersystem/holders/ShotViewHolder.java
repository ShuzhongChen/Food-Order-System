package com.shuzhongchen.foodordersystem.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shuzhongchen.foodordersystem.R;

/**
 * Created by shuzhongchen on 5/6/18.
 */

public class ShotViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

    public TextView price;
    public TextView title;

    public ImageView image;



    public ShotViewHolder(View view) {
        super(view);

        price = (TextView) view.findViewById(R.id.customer_menu_price);
        title = (TextView) view.findViewById(R.id.customer_menu_title);
        image = (ImageView) view.findViewById(R.id.customer_menu_image);
    }

    @Override
    public void onClick(View view) {
        
    }
}
