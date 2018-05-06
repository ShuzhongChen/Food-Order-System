package com.shuzhongchen.foodordersystem.view.child;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.shuzhongchen.foodordersystem.R;
import com.shuzhongchen.foodordersystem.model.Shot;

import java.util.List;

/**
 * Created by shuzhongchen on 5/6/18.
 */

public class ShotListAdapter extends RecyclerView.Adapter {

    private List<Shot> data;

    public ShotListAdapter(@NonNull List<Shot> data) {
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_iterm_shot, parent, false);
        return new ShotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Shot shot = data.get(position);
        ((ShotViewHolder) holder).shotViewText.setText(shot.text);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
