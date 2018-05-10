package com.shuzhongchen.foodordersystem;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.shuzhongchen.foodordersystem.interfaces.ItemClickListener;

/**
 * Created by mengtongma on 5/3/18.
 */

public class OrderViewHolder extends RecyclerView.ViewHolder {

    public TextView txtOrderId, txtOrderStatus, txtOrderCancel;

    //private ItemClickListener itemClickListener;



    public OrderViewHolder(View itemView) {
        super(itemView);

        txtOrderId = itemView.findViewById(R.id.order_id);
        txtOrderStatus = itemView.findViewById(R.id.order_status);
        txtOrderCancel = itemView.findViewById(R.id.order_cancel);

//        itemView.setOnClickListener(this);

    }

//    public void setItemClickListener(ItemClickListener itemClickListener) {
//        this.itemClickListener = itemClickListener;
//    }
//
//    @Override
//    public void onClick(View view) {
//        itemClickListener.onClick(view, getAdapterPosition(), false);
//    }
}
