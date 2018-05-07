package com.shuzhongchen.foodordersystem.view;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by shuzhongchen on 5/3/18.
 */

public class FoodOrderApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
