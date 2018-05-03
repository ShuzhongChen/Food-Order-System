package com.shuzhongchen.foodordersystem.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.shuzhongchen.foodordersystem.R;
import com.shuzhongchen.foodordersystem.interfaces.ItemClickListener;

import info.hoang8f.widget.FButton;

/**
 * Created by jinchengcheng on 5/2/18.
 */

public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView nameTV;
        public TextView categoryTV;
        public TextView caloriesTV;
        public TextView UnitPriceTV;
        public TextView PrepTimeTV;
        public TextView idTV;

        public ImageButton imageButton;
        public Button editButton;
        public Button removeButton;



        private ItemClickListener itemClickListener;

        public MenuViewHolder(View view) {
            super(view);
            idTV = (TextView)view.findViewById(R.id.menu_id_textView);
            nameTV = (TextView)view.findViewById(R.id.menu_name_textView);
            categoryTV = (TextView)view.findViewById(R.id.menu_category_textview);
            caloriesTV = (TextView) view.findViewById(R.id.menu_calories_textView);
            UnitPriceTV = (TextView) view.findViewById(R.id.menu_unit_price_textView);
            PrepTimeTV = (TextView) view.findViewById(R.id.menu_preptime_textView);
            imageButton = (ImageButton) view.findViewById(R.id.menu_image);

            editButton = (Button)view.findViewById(R.id.edit_fbutton);
            removeButton = (Button) view.findViewById(R.id.remove_fbutton);

        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view, getAdapterPosition(), false);
        }
}
