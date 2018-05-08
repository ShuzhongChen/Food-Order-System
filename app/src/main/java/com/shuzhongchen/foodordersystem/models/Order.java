package com.shuzhongchen.foodordersystem.models;

/**
 * Created by jinchengcheng on 5/8/18.
 */

public class Order {
    private String orderPlaceTime;
    private String pickupTime;
    private String readyTime;
    private String status;
    private int totalPrice;
    private OrderContent orderContent;

    public String getOrderPlaceTime() {
        return orderPlaceTime;
    }

    public Order setOrderPlaceTime(String orderPlaceTime) {
        this.orderPlaceTime = orderPlaceTime;
        return this;
    }

    public String getPickupTime() {
        return pickupTime;
    }

    public Order setPickupTime(String pickupTime) {
        this.pickupTime = pickupTime;
        return this;
    }

    public String getReadyTime() {
        return readyTime;
    }

    public Order setReadyTime(String readyTime) {
        this.readyTime = readyTime;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public Order setStatus(String status) {
        this.status = status;
        return this;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public Order setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public OrderContent getOrderContent() {
        return orderContent;
    }

    public Order setOrderContent(OrderContent orderContent) {
        this.orderContent = orderContent;
        return this;
    }
}
