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

    public int getUnitPrice() {
        return unitprice;
    }

    public Menu setUnitPrice(int unitPrice) {
        this.unitprice = unitPrice;
        return this;
    }

    public int getPrepTime() {
        return preptime;
    }

    public Menu setPrepTime(int prepTime) {
        this.preptime = prepTime;
        return this;
    }

    public Menu setImage(String image) {
        this.image = image;
        return this;
    }

    public String getImage() {
        return this.image;
    }
}
