package com.shuzhongchen.foodordersystem.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.shuzhongchen.foodordersystem.R;
import com.shuzhongchen.foodordersystem.interfaces.ItemClickListener;

/**
 * Created by jinchengcheng on 5/18/18.
 */

public class AdminOrderHolder extends RecyclerView.ViewHolder {

    public TextView OrderId;
    public TextView UserEmail;
    public TextView StartTime;
    public TextView PickupTime;
    public TextView ReadyTime;
    public TextView Status;
    public TextView TotalPrice;
    public ListView OrderContent;


    private ItemClickListener itemClickListener;

    public AdminOrderHolder(View view) {
        super(view);
        UserEmail = (TextView) view.findViewById(R.id.user_email_textview);
       OrderId = (TextView) view.findViewById(R.id.order_id_textview);
       StartTime = (TextView) view.findViewById(R.id.start_time_textview);
       PickupTime = (TextView) view.findViewById(R.id.pickup_time_textview);
       ReadyTime = (TextView) view.findViewById(R.id.ready_time_textview);
       Status = (TextView) view.findViewById(R.id.status_textview);
       TotalPrice = (TextView)view.findViewById(R.id.total_price_textview);
       OrderContent = (ListView)view.findViewById(R.id.order_content_textview);

    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

}
