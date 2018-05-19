package com.shuzhongchen.foodordersystem.activities;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.wifi.aware.Characteristics;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.shuzhongchen.foodordersystem.R;
import com.shuzhongchen.foodordersystem.adapter.AdminOrderAdapter;
import com.shuzhongchen.foodordersystem.holders.AdminOrderHolder;
import com.shuzhongchen.foodordersystem.models.FoodInOrder;
import com.shuzhongchen.foodordersystem.models.Order;
import com.shuzhongchen.foodordersystem.models.OrderContent;
import com.shuzhongchen.foodordersystem.view.base.AdminOrderSortFragment;
import com.shuzhongchen.foodordersystem.view.base.AdminPopularityFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by jinchengcheng on 5/14/18.
 */

public class MenuSortActivity extends AppCompatActivity {

    TextView startDatePicker;
    TextView endDatePicker;
    Button nextButton;
    Button popularityButton;
    Calendar myCalendar;

    DatePickerDialog datePickerDialog;

    int startDay;
    int startMonth;
    int startYear;

    int endDay;
    int endMonth;
    int endYear;

    boolean startDateChoosed;
    boolean endDateChoosed;


    public RecyclerView OrderRecyclerView;
    public RecyclerView.LayoutManager OrderLayoutManager;

    FirebaseDatabase database;
    DatabaseReference orders;

    private List<Order> listOfOrder;
    private List<Order> choosedOrder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_choose_date);
        System.out.println("OnCreateView Pressed!" + "\n");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        startDatePicker = (TextView) findViewById(R.id.startDate);
        endDatePicker = (TextView) findViewById(R.id.endDate);
        nextButton = (Button) findViewById(R.id.nextButton);
        popularityButton = (Button) findViewById(R.id.popularityButton);
        myCalendar = Calendar.getInstance();

        startDay = myCalendar.get(Calendar.DAY_OF_MONTH);
        startMonth = myCalendar.get(Calendar.MONTH);
        startYear = myCalendar.get(Calendar.YEAR);

        startDateChoosed = false;
        endDateChoosed = false;

        database = FirebaseDatabase.getInstance();
        orders = database.getReference("Orders");

//        OrderRecyclerView = (RecyclerView) findViewById(R.id.AdminListOrders);
//        OrderLayoutManager = new LinearLayoutManager(this);
//        OrderRecyclerView.setHasFixedSize(true);
//        OrderRecyclerView.setLayoutManager(OrderLayoutManager);
        listOfOrder = new ArrayList<>();
        choosedOrder = new ArrayList<>();


        startDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("start date picker clicked " + "\n");

                // calender class's instance and get current date , month and year from calender

                int mYear = myCalendar.get(Calendar.YEAR); // current year
                int mMonth = myCalendar.get(Calendar.MONTH); // current month
                int mDay = myCalendar.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(MenuSortActivity.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // set day of month , month and year value in the edit text
                        System.out.println("day of month: "  + dayOfMonth + "month of year: " + monthOfYear
                         + "year: " + year + "\n");

                        startDatePicker.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                        startDay = dayOfMonth;
                        startMonth = monthOfYear + 1;
                        startYear = year;
                        startDateChoosed = true;

                    }
                }, mYear, mMonth, mDay);


                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

                datePickerDialog.show();
            }

        });

        endDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("start date picker clicked " + "\n");

                if (startDateChoosed) {

                    // date picker dialog
                    datePickerDialog = new DatePickerDialog(MenuSortActivity.this, new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            endDatePicker.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            endDay = dayOfMonth;
                            endMonth = monthOfYear;
                            endYear = year;
                            endDateChoosed = true;

                        }
                    }, startYear, startMonth, startDay);

                    Calendar c = Calendar.getInstance();
                    c.set(startYear, startMonth, startDay);
                    datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
                    c.add(Calendar.DATE, +7);
                    datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());

                    datePickerDialog.show();

                } else {
                    Toast.makeText(MenuSortActivity.this, "please choose start date first", Toast.LENGTH_LONG).show();
                }
            }

        });


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!startDateChoosed || !endDateChoosed) {
                    Toast.makeText(MenuSortActivity.this, "please choose date", Toast.LENGTH_LONG).show();
                } else {
                    choosedOrder.clear();
                    for (Order o : listOfOrder) {
                        if (duringTheDate(o.getOrderPlaceTime())) {
                            choosedOrder.add(o);
                        }
                    }

                    Fragment fragment = AdminOrderSortFragment.newInstance(getApplicationContext(), choosedOrder);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.orderContainer, fragment)
                            .commit();
                    Toast.makeText(getApplicationContext(), choosedOrder.size() + " orders have been loaded", Toast.LENGTH_LONG).show();
                    //loadOrder(choosedOrder);

                }

            }
        });

        popularityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                choosedOrder.clear();
                for (Order o : listOfOrder) {
                    if (duringTheDate(o.getOrderPlaceTime())) {
                        choosedOrder.add(o);
                    }
                }

                Fragment fragment = AdminPopularityFragment.newInstance(getApplicationContext(), choosedOrder);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.orderContainer, fragment)
                        .commit();
                //Toast.makeText(getApplicationContext(), choosedOrder.size() + " orders have been loaded", Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(), choosedOrder.size() + " orders have been loaded in popularity report", Toast.LENGTH_LONG).show();
            }
        });

        orders.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Log.e("Count " ,""+snapshot.getChildrenCount());
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Order order = postSnapshot.getValue(Order.class);
                        listOfOrder.add(order);
                }
                System.out.println("list size: " + listOfOrder.size() + "\n");
            }


            @Override
            public void onCancelled(DatabaseError firebaseError) {
                Log.e("The read failed: " ,firebaseError.getMessage());
            }
        });

    }

//    private void loadOrder(List<Order> choosedOrder) {
//
//        RecyclerView.Adapter adapter = new AdminOrderAdapter( getApplicationContext(), choosedOrder);
//        OrderRecyclerView.setAdapter(adapter);
//    }


    private boolean duringTheDate(String date) {
        String[] str = date.split("/");
        int month = Integer.parseInt(str[1]);
        int day = Integer.parseInt(str[0]);

        if (month < startMonth || month > endMonth) {
            return false;
        } else if (month == startMonth ) {
            if (day < startDay) {
                return false;
            }
        } else if (month == endMonth) {
            if (day > endDay) {
                return false;
            }
        }

        return true;
    }

}

