package com.shuzhongchen.foodordersystem.view.child;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import com.shuzhongchen.foodordersystem.R;
import com.shuzhongchen.foodordersystem.view.base.BaseViewHolder;

/**
 * Created by shuzhongchen on 5/6/18.
 */

public class ShotViewHolder extends RecyclerView.ViewHolder {

    TextView shotViewText;

    public ShotViewHolder(@NonNull View itemView) {
        super(itemView);

        shotViewText = (TextView) itemView.findViewById(R.id.main_list_item_text);
    }
}

