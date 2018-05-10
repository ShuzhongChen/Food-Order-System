package com.shuzhongchen.foodordersystem.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.reflect.TypeToken;
import com.shuzhongchen.foodordersystem.R;
import com.shuzhongchen.foodordersystem.helper.ModelUtils;
import com.shuzhongchen.foodordersystem.holders.MenuOrderViewHolder;
import com.shuzhongchen.foodordersystem.holders.ShotViewHolder;
import com.shuzhongchen.foodordersystem.models.FoodInOrder;
import com.shuzhongchen.foodordersystem.models.Menu;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by shuzhongchen on 5/10/18.
 */

public class ShotListAdapter extends RecyclerView.Adapter {

    private List<Menu> data;
    private String MODEL_FOODLIST = "food_list";
    private Context context;

    public ShotListAdapter(@NonNull List<Menu> data) {
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_iterm_shot, parent, false);
        context = parent.getContext();
        return new ShotViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        Menu menu = data.get(position);
        Log.d("Shuzhong debug menu", menu.getName());
        final String name = menu.getName();
        final int price = menu.getUnitprice();
        final String id = menu.getUuid();
        final int preptime = menu.getPreptime();
        ((ShotViewHolder) holder).price.setText(price + "");
        ((ShotViewHolder) holder).title.setText(name);
        Picasso.get().load(menu.getImage())
                .into(((ShotViewHolder) holder).image);

        boolean containsFood = false;

        List<FoodInOrder> foodList = ModelUtils.read(context,
                MODEL_FOODLIST,
                new TypeToken<List<FoodInOrder>>(){});

        for (int i = 0; i < foodList.size(); i++) {
            if (foodList.get(i).name.equals(name)) {
                containsFood = true;
                break;
            }
        }

        if (containsFood) {
            ((ShotViewHolder) holder).btn.setImageResource(R.drawable.ic_check_black_24dp);
        } else {
            ((ShotViewHolder) holder).btn.setImageResource(R.drawable.ic_add_black_24dp);
        }
        ((ShotViewHolder) holder).btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean containsFood = false;
                int index = 0;

                List<FoodInOrder> foodList = ModelUtils.read(context,
                        MODEL_FOODLIST,
                        new TypeToken<List<FoodInOrder>>(){});

                for (int i = 0; i < foodList.size(); i++) {
                    if (foodList.get(i).id.equals(id)) {
                        containsFood = true;
                        index = i;
                        break;
                    }
                }


                if (containsFood) {
                    foodList.remove(index);

                    ModelUtils.save(context, MODEL_FOODLIST, foodList);
                    ((ShotViewHolder) holder).btn.setImageResource(R.drawable.ic_add_black_24dp);
                } else {
                    foodList.add(new FoodInOrder(id, name, price, 1, preptime));

                    ModelUtils.save(context, MODEL_FOODLIST, foodList);
                    ((ShotViewHolder) holder).btn.setImageResource(R.drawable.ic_check_black_24dp);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
