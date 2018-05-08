package com.shuzhongchen.foodordersystem.helper;

import com.shuzhongchen.foodordersystem.models.FoodInOrder;

import java.util.ArrayList;

/**
 * Created by shuzhongchen on 5/8/18.
 */

public interface FragmentCommunication {
    public void passIndex(ArrayList<FoodInOrder> list);
}
