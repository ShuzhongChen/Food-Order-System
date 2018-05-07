package com.shuzhongchen.foodordersystem.models;

import java.net.URL;

/**
 * Created by jinchengcheng on 4/30/18.
 */

public class Menu {
    private String name;
    private String category;
    private int calories;
    private int unitprice;
    private int preptime;
    private String image;

    public int getOrdertimes() {
        return ordertimes;
    }

    public Menu setOrdertimes(int ordertimes) {
        this.ordertimes = ordertimes;
        return this;
    }

    private int ordertimes;

    public String getName() {
        return name;
    }

    public Menu setName(String name) {
        this.name = name;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public Menu setCategory(String category) {
        this.category = category;
        return this;
    }

    public int getCalories() {
        return calories;
    }

    public Menu setCalories(int calories) {
        this.calories = calories;
        return this;
    }

    public int getUnitprice() {
        return unitprice;
    }

    public Menu setUnitprice(int unitprice) {
        this.unitprice = unitprice;
        return this;
    }

    public int getPreptime() {
        return preptime;
    }

    public Menu setPreptime(int preptime) {
        this.preptime = preptime;
        return this;
    }

    public String getImage() {
        return image;
    }

    public Menu setImage(String image) {
        this.image = image;
        return this;
    }

}