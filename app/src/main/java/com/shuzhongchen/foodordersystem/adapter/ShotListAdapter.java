package com.shuzhongchen.foodordersystem.adapter;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.shuzhongchen.foodordersystem.R;
import com.shuzhongchen.foodordersystem.activities.AdminDashboardActivity;
import com.shuzhongchen.foodordersystem.activities.CustomerActivity;
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
        final Menu menu = data.get(position);

        final String name = menu.getName();
        final int price = menu.getUnitprice();
        final String id = menu.getUuid();
        final int preptime = menu.getPreptime();

        ((ShotViewHolder) holder).price.setText(price + "");
        ((ShotViewHolder) holder).title.setText(name);

        Picasso.get().load(menu.getImage())
                .into(((ShotViewHolder) holder).image);


        ((ShotViewHolder) holder).image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder create_menu_dialog = new AlertDialog.Builder(context);
                create_menu_dialog.setTitle(menu.getName());

                LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View CreateView = layoutInflater.inflate(R.layout.activity_item_details,null);

                create_menu_dialog.setView(CreateView);

                ImageView imageView = CreateView.findViewById(R.id.itemPic);
                Picasso.get().load(menu.getImage())
                        .into(imageView);

                TextView txtCategory = CreateView.findViewById(R.id.txtCategory);
                String str = menu.getCategory();
                str = str.substring(0, 1).toUpperCase() + str.substring(1);
                txtCategory.setText(str);

                TextView txtName = CreateView.findViewById(R.id.txtName);
                txtName.setText(menu.getName());

                TextView txtPrice = CreateView.findViewById(R.id.txtPrice);
                txtPrice.setText(menu.getUnitprice() + "");

                TextView txtCalories = CreateView.findViewById(R.id.txtCalories);
                txtCalories.setText(menu.getCalories() + "");

                create_menu_dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                create_menu_dialog.show();
            }
        });




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
