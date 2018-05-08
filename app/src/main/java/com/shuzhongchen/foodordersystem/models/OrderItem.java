package com.shuzhongchen.foodordersystem.models;

/**
 * Created by jinchengcheng on 5/8/18.
 */

public class OrderItem {
    private String itemsId;
    private int quantities;
    private int unitprice;

    public String getItemsId() {
        return itemsId;
    }

    public OrderItem setItemsId(String itemsId) {
        this.itemsId = itemsId;
        return this;
    }

    public int getQuantities() {
        return quantities;
    }

    public OrderItem setQuantities(int quantities) {
        this.quantities = quantities;
        return this;
    }

    public int getUnitprice() {
        return unitprice;
    }

    public OrderItem setUnitprice(int unitprice) {
        this.unitprice = unitprice;
        return this;
    }

}
