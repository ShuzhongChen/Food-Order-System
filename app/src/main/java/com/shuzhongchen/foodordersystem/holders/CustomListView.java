package com.shuzhongchen.foodordersystem.holders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.shuzhongchen.foodordersystem.R;

import info.hoang8f.widget.FButton;

/**
 * Created by jinchengcheng on 4/29/18.
 */

public class CustomListView extends ArrayAdapter<String>{

    private Context context;
    private String[] listOfName;
    private String[] listOfCategory;
    private int[] listOfCalories;
    private int[] listOfUnitPrice;
    private int[] listOfPrepTime;

    public CustomListView(@NonNull Context context,String[] listOfName, String[] listOfCategory,
                          int[] listOfCalories, int[] listOfUnitPrice, int[] listOfPrepTime) {
        super(context, R.layout.menu_dispaly, listOfName);

        this.listOfName = listOfName;
        this.listOfCalories = listOfCalories;
        this.listOfCategory = listOfCategory;
        this.listOfUnitPrice = listOfUnitPrice;
        this.listOfPrepTime = listOfPrepTime;

        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View r = convertView;
        viewHolder vh = null;

        if (vh == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            r = layoutInflater.inflate(R.layout.menu_dispaly, null, true);

            vh = new viewHolder(r);
            r.setTag(1);
        } else {
            vh = (viewHolder)r.getTag(1);
        }

        vh.nameTV.setText(listOfName[position]);
        vh.caloriesTV.setText(String.valueOf(listOfCalories[position]));
        vh.categoryTV.setText(listOfCategory[position]);
        vh.UnitPriceTV.setText(String.valueOf(listOfUnitPrice[position]));
        vh.PrepTimeTV.setText(String.valueOf(listOfPrepTime[position]));

        return r;
    }


    class viewHolder {

        TextView nameTV;
        TextView categoryTV;
        TextView caloriesTV;
        TextView UnitPriceTV;
        TextView PrepTimeTV;
        ImageButton imageButton;

        viewHolder(View view) {
            nameTV = (TextView)view.findViewById(R.id.menu_name_textView);
            categoryTV = (TextView)view.findViewById(R.id.menu_category_textview);
            caloriesTV = (TextView) view.findViewById(R.id.menu_calories_textView);
            UnitPriceTV = (TextView) view.findViewById(R.id.menu_unit_price_textView);
            PrepTimeTV = (TextView) view.findViewById(R.id.menu_preptime_textView);
            imageButton = (ImageButton) view.findViewById(R.id.menu_image);
        }
    }

    /**
     * Created by jinchengcheng on 5/1/18.
     */

    public static class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

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
}
