package com.shuzhongchen.foodordersystem;

/**
 * Created by jinchengcheng on 4/30/18.
 */

public class Menu {

    private int id;
    private String name;
    private String category;
    private int calories;
    private int unitPrice;
    private int prepTime;

    public int getId() {
        return id;
    }

    public Menu setId(int id) {
        this.id = id;
        return this;
    }

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
        return unitPrice;
    }

    public Menu setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }

    public int getPrepTime() {
        return prepTime;
    }

    public Menu setPrepTime(int prepTime) {
        this.prepTime = prepTime;
        return this;
    }
}
