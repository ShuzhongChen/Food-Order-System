package com.shuzhongchen.foodordersystem;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jinchengcheng on 4/27/18.
 */

public class addMenuItemFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View View = inflater.inflate(R.layout.activity_menu_detail, container, false);

        System.out.print("start add items view");
        return View;
    }
}
