package com.shuzhongchen.foodordersystem.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.shuzhongchen.foodordersystem.R;
import com.shuzhongchen.foodordersystem.holders.AdminOrderHolder;
import com.shuzhongchen.foodordersystem.models.FoodInOrder;
import com.shuzhongchen.foodordersystem.models.Order;
import com.shuzhongchen.foodordersystem.models.OrderContent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jinchengcheng on 5/19/18.
 */

public class AdminOrderAdapter extends RecyclerView.Adapter {

        private List<Order> data;
        private List<String> key;

        public AdminOrderAdapter(@NonNull List<Order> data) {
            this.data = data;
        }

        private Context context;

        public AdminOrderAdapter(Context context, @NonNull List<Order> data, @NonNull List<String> orderKey) {
            this.context = context;
            this.data = data;
            this.key = orderKey;

        }


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.admin_order_display, parent, false);
            return new AdminOrderHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            Order model = data.get(position);
            ((AdminOrderHolder)holder).TotalPrice.setText("" + model.getTotalPrice());
            ((AdminOrderHolder)holder).Status.setText("" + model.getStatus());
            ((AdminOrderHolder)holder).PickupTime.setText("" + model.getPickupTime());
            ((AdminOrderHolder)holder).StartTime.setText("" + model.getStartTime());
            ((AdminOrderHolder)holder).OrderId.setText("" + key.get(position));
            ((AdminOrderHolder)holder).ReadyTime.setText("" + model.getReadyTime());

            List<String> content = new ArrayList<>();

            OrderContent c = model.getOrderContent();
            List<FoodInOrder> foodInOrder = c.getOrderItems();

            for (FoodInOrder f : foodInOrder) {
                String str = "Items: " + f.getName() + "\n"
                        + "Quantities: " + f.getNum();
                content.add(str);
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                    R.layout.listview_style,
                    content);


            ViewGroup.LayoutParams params = ((AdminOrderHolder)holder).OrderContent.getLayoutParams();
            params.height = 200 * foodInOrder.size();
            ((AdminOrderHolder)holder).OrderContent.setLayoutParams(params);
            ((AdminOrderHolder)holder).OrderContent.requestLayout();

            ((AdminOrderHolder)holder).OrderContent.setAdapter(adapter);
        }


        @Override
        public int getItemCount() {
            return data.size();
        }


}
