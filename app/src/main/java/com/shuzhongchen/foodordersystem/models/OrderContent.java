package com.shuzhongchen.foodordersystem.models;

import java.util.List;

/**
 * Created by jinchengcheng on 5/8/18.
 */

public class OrderContent {

    private List<OrderItem> orderItems;

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }


}
