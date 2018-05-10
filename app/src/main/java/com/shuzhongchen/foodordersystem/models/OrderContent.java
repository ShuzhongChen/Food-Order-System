package com.shuzhongchen.foodordersystem.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jinchengcheng on 5/8/18.
 */

public class OrderContent {

    private List<FoodInOrder> orderItems;

    public OrderContent() {
        orderItems = new ArrayList<>();
    }

    public List<FoodInOrder> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<FoodInOrder> orderItems) {
        this.orderItems = orderItems;
    }


}
