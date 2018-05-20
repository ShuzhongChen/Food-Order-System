package com.shuzhongchen.foodordersystem.view.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import com.shuzhongchen.foodordersystem.adapter.OrderSortAdapter;
import com.shuzhongchen.foodordersystem.models.FoodInOrder;
import com.shuzhongchen.foodordersystem.models.Menu;
import com.shuzhongchen.foodordersystem.models.Order;
import com.shuzhongchen.foodordersystem.view.child.AdminPopularityFragmentChild;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jinchengcheng on 5/19/18.
 */

public class AdminPopularityFragment extends Fragment {
    
    public RecyclerView.LayoutManager layoutManager;

    private static List<Order> listOfOrder;
    private static ArrayList<Menu> menus;

    private static Context fragmentContext;

    private ArrayList<Menu> appetizerMenu;
    private ArrayList<Menu> dessertMenu;
    private ArrayList<Menu> drinkMenu;
    private ArrayList<Menu> mainCourseMenu;


    private Map<String, Integer> MenuCountMap;

    public static AdminPopularityFragment newInstance(Context context, List<Order> choosedOrders, ArrayList<Menu> menuList) {
        Bundle args = new Bundle();
        AdminPopularityFragment fragment = new AdminPopularityFragment();
        fragment.setArguments(args);
        fragmentContext = context;
        listOfOrder = choosedOrders;
        menus = menuList;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewRoot = inflater.inflate(R.layout.admin_popularity_fragment, container, false);

        appetizerMenu = new ArrayList<>();
        dessertMenu = new ArrayList<>();
        drinkMenu = new ArrayList<>();
        mainCourseMenu = new ArrayList<>();


        MenuCountMap = new HashMap<>();

        initiateMenu(); //set menu order time to 0 so we can calculate how many times menu has been orders
        sortOrder(listOfOrder);

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
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                fm.beginTransaction();
                Fragment fragTwo = null;

                Toast.makeText(fragmentContext, "values: " + values[position], Toast.LENGTH_LONG).show();
                switch (position) {
                    case 0:
                        Toast.makeText(fragmentContext, "please choose category first!", Toast.LENGTH_LONG).show();
                        break;
                    case 1:

                        fragTwo = AdminPopularityFragmentChild.newInstance(fragmentContext, appetizerMenu);

                        System.out.println("size1: " + appetizerMenu.size() + "\n");
                        break;
                    case 2:
                        fragTwo = AdminPopularityFragmentChild.newInstance(fragmentContext, mainCourseMenu);

                        System.out.println("size2: " + mainCourseMenu.size() + "\n");
                        break;
                    case 3:
                        fragTwo = AdminPopularityFragmentChild.newInstance(fragmentContext, drinkMenu);
                        break;
                    case 4:
                        fragTwo = AdminPopularityFragmentChild.newInstance(fragmentContext, dessertMenu);
                        break;
                }

                if (fragTwo != null) {
                    ft.replace(R.id.fragment2, fragTwo);
                    ft.commit();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });
        return viewRoot;
    }

    private void sortOrder(List<Order> listOfOrder) {


        for (Order o : listOfOrder) {
            List<FoodInOrder> list = o.getOrderContent().getOrderItems();
            for (FoodInOrder f : list) {

                if (!MenuCountMap.containsKey(f.getId())) {
                    MenuCountMap.put(f.getId(), f.getNum());
                } else {
                    MenuCountMap.put(f.getId(), MenuCountMap.get(f.getId()) + f.getNum());
                }

            }
        }

        for (Menu m : menus) {

            if (MenuCountMap.containsKey(m.getUuid())) {
                m.setOrdertimes(MenuCountMap.get(m.getUuid()));

                if (m.getCategory().equalsIgnoreCase("appetizer")) {
                    appetizerMenu.add(m);

                } else if (m.getCategory().equalsIgnoreCase("drink")) {
                    drinkMenu.add(m);

                } else if (m.getCategory().equalsIgnoreCase("main course")) {
                    mainCourseMenu.add(m);

                } else if (m.getCategory().equalsIgnoreCase("dessert")) {
                    dessertMenu.add(m);

                }
            }
        }
    }


    private void initiateMenu() {
        for (Menu m : menus) {
            m.setOrdertimes(0);
        }
        System.out.println("menu size in initiate: " + menus.size());
    }


}

