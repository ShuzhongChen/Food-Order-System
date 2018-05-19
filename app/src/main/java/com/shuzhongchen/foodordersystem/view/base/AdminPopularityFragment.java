package com.shuzhongchen.foodordersystem.view.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.shuzhongchen.foodordersystem.R;
import com.shuzhongchen.foodordersystem.adapter.AdminOrderAdapter;
import com.shuzhongchen.foodordersystem.models.FoodInOrder;
import com.shuzhongchen.foodordersystem.models.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jinchengcheng on 5/19/18.
 */

public class AdminPopularityFragment extends Fragment {

    public RecyclerView OrderRecyclerView;
    public RecyclerView.LayoutManager OrderLayoutManager;

    private static List<Order> listOfOrder;

    private static Context fragmentContext;

    private List<FoodInOrder> appetizerOrder;
    private int appetizerCount;
    private List<FoodInOrder> dessertOrder;
    private int dessertCount;
    private List<FoodInOrder> drinkOrder;
    private int drinkCount;
    private List<FoodInOrder> mainCourseOrder;
    private int mainCourseCount;

    public static AdminPopularityFragment newInstance(Context context, List<Order> choosedOrders) {
        Bundle args = new Bundle();
        AdminPopularityFragment fragment = new AdminPopularityFragment();
        fragment.setArguments(args);
        fragmentContext = context;
        listOfOrder = choosedOrders;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewRoot = inflater.inflate(R.layout.admin_popularity_fragment, container, false);

        OrderRecyclerView = (RecyclerView) viewRoot.findViewById(R.id.AdminListPopularity);
        OrderLayoutManager = new LinearLayoutManager(fragmentContext);
        OrderRecyclerView.setHasFixedSize(true);
        OrderRecyclerView.setLayoutManager(OrderLayoutManager);

        appetizerOrder = new ArrayList<>();
        dessertOrder = new ArrayList<>();
        drinkOrder = new ArrayList<>();
        mainCourseOrder = new ArrayList<>();

        appetizerCount = 0;
        dessertCount = 0;
        drinkCount = 0;
        mainCourseCount = 0;

        final String [] values =
                {"-- please choose --", "appetizer","main course","drinks","dessert"};
        Spinner spinner = (Spinner) viewRoot.findViewById(R.id.mainSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(fragmentContext, "values: " + values[position], Toast.LENGTH_LONG).show();
                switch (position) {
                    case 0:
                        Toast.makeText(fragmentContext, "please choose category first!", Toast.LENGTH_LONG).show();
                    case 1:
                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(fragmentContext, "please choose category first!", Toast.LENGTH_LONG).show();

            }
        });
        return viewRoot;
    }

    private void sortOrder(List<Order> listOfOrder) {
        for (Order o : listOfOrder) {
            List<FoodInOrder> list = o.getOrderContent().getOrderItems();
            for (FoodInOrder f : list) {
                if (f.getCategory().equalsIgnoreCase("appetizer")) {
                    appetizerOrder.add(f);
                    appetizerCount += f.getNum();
                } else if (f.getCategory().equalsIgnoreCase("drink")) {
                    drinkOrder.add(f);
                    drinkCount += f.getNum();
                } else if (f.getCategory().equalsIgnoreCase("main course")) {
                    mainCourseOrder.add(f);
                    mainCourseCount += f.getNum();
                } else if (f.getCategory().equalsIgnoreCase("dessert")) {
                    drinkOrder.add(f);
                    drinkCount += f.getNum();
                }
            }
        }
    }

    private void loadOrder(List<Order> choosedOrder) {
        // to do

        RecyclerView.Adapter adapter = new AdminOrderAdapter( fragmentContext, choosedOrder);
        OrderRecyclerView.setAdapter(adapter);
    }

//    private List<Menu> sortList(List<Menu> newList, int p) {
//        switch (p) {
//            case 2:
//                Collections.sort(newList, new Comparator<Menu>() {
//                    @Override
//                    public int compare(Menu m1, Menu m2) {
//                        return m1.getUnitprice() - m2.getUnitprice();
//                    }
//                });
//                break;
//
//            case 1:
//                Collections.sort(newList, new Comparator<Menu>() {
//                    @Override
//                    public int compare(Menu m1, Menu m2) {
//                        return m1.getName().compareTo(m2.getName());
//                    }
//                });
//                break;
//
//            case 0:
//                Collections.sort(newList, new Comparator<Menu>() {
//                    @Override
//                    public int compare(Menu m1, Menu m2) {
//                        return m1.getOrdertimes() - m2.getOrdertimes();
//                    }
//                });
//                break;
//        }
//
//        return newList;
//
//    }

}

