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
    public String price;
    public int num;


    public FoodInOrder(String id, String name, String price, int num) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.num = num;
    }

    protected FoodInOrder(Parcel in) {
        id = in.readString();
        name = in.readString();
        price = in.readString();
        num = in.readInt();
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
        parcel.writeString(price);
        parcel.writeInt(num);
    }
}
