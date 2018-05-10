package com.shuzhongchen.foodordersystem.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.net.URL;

/**
 * Created by jinchengcheng on 4/30/18.
 */

public class Menu implements Parcelable {
    public String name;
    public String category;
    public int calories;
    public int unitprice;
    public int preptime;
    public String image;
    public int ordertimes;
    public String uuid;

    public Menu(){

    }

    protected Menu(Parcel in) {
        name = in.readString();
        category = in.readString();
        calories = in.readInt();
        unitprice = in.readInt();
        preptime = in.readInt();
        image = in.readString();
        ordertimes = in.readInt();
        uuid = in.readString();
    }

    public static final Creator<Menu> CREATOR = new Creator<Menu>() {
        @Override
        public Menu createFromParcel(Parcel in) {
            return new Menu(in);
        }

        @Override
        public Menu[] newArray(int size) {
            return new Menu[size];
        }
    };

    public String getUuid() {
        return uuid;
    }

    public Menu setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public int getOrdertimes() {
        return ordertimes;
    }

    public Menu setOrdertimes(int ordertimes) {
        this.ordertimes = ordertimes;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(category);
        parcel.writeInt(calories);
        parcel.writeInt(unitprice);
        parcel.writeInt(preptime);
        parcel.writeString(image);
        parcel.writeInt(ordertimes);
        parcel.writeString(uuid);
    }
}