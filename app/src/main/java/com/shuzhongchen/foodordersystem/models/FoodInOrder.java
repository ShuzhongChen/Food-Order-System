package com.shuzhongchen.foodordersystem.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import java.util.UUID;

/**
 * Created by shuzhongchen on 5/8/18.
 */

public class FoodInOrder implements Parcelable {

    public String id;
    public String name;
    public int price;
    private int preptime;
    public int num;

    public FoodInOrder() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPreptime() {
        return preptime;
    }

    public void setPreptime(int preptime) {
        this.preptime = preptime;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }


    public FoodInOrder(String id, String name, int price, int num, int preptime) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.num = num;
        this.preptime = preptime;
    }

    protected FoodInOrder(Parcel in) {
        id = in.readString();
        name = in.readString();
        price = in.readInt();
        num = in.readInt();
        preptime = in.readInt();
    }

    public static final Creator<FoodInOrder> CREATOR = new Creator<FoodInOrder>() {
        @Override
        public FoodInOrder createFromParcel(Parcel in) {
            return new FoodInOrder(in);
        }

        @Override
        public FoodInOrder[] newArray(int size) {
            return new FoodInOrder[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeInt(price);
        parcel.writeInt(num);
        parcel.writeInt(preptime);
    }
}
