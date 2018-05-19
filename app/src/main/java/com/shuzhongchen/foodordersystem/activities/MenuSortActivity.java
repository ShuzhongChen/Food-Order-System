package com.shuzhongchen.foodordersystem.activities;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.wifi.aware.Characteristics;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.shuzhongchen.foodordersystem.R;
import com.shuzhongchen.foodordersystem.view.base.AdminOrderSortFragment;

import java.util.Calendar;

/**
 * Created by jinchengcheng on 5/14/18.
 */

public class MenuSortActivity extends AppCompatActivity {

    TextView startDatePicker;
    TextView endDatePicker;
    Button nextButton;
    Calendar myCalendar;

    DatePickerDialog datePickerDialog;


    int startDay;
    int startMonth;
    int startYear;

    boolean startDateChoosed;
    boolean endDateChoosed;

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
        myCalendar = Calendar.getInstance();

        startDay = myCalendar.get(Calendar.DAY_OF_MONTH);
        startMonth = myCalendar.get(Calendar.MONTH);
        startYear = myCalendar.get(Calendar.YEAR);

        startDateChoosed = false;
        endDateChoosed = false;

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
                        startMonth = monthOfYear;
                        startYear = year;
                        startDateChoosed = true;

                    }
                }, mYear, mMonth, mDay);


                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
//                Calendar cal = Calendar.getInstance();
//                cal.add(Calendar.DAY_OF_WEEK, +7);
//                datePickerDialog.getDatePicker().setMaxDate(cal.getTimeInMillis());

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
                            endDateChoosed = true;

                        }
                    }, startYear, startMonth, startDay);


                    datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.DAY_OF_WEEK, +7);
                    datePickerDialog.getDatePicker().setMaxDate(cal.getTimeInMillis());

                    datePickerDialog.show();

                } else {
                    Toast.makeText(MenuSortActivity.this, "please choose start date first", Toast.LENGTH_LONG).show();
                }
            }

        });


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String startDate = startDatePicker.getText().toString();
                String endDate = endDatePicker.getText().toString();


                if (!startDateChoosed || !endDateChoosed) {
                    Toast.makeText(MenuSortActivity.this, "please choose date", Toast.LENGTH_LONG).show();
                } else {
                    AdminOrderSortFragment fragment = AdminOrderSortFragment.newInstance();

                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.orderContainer, fragment)
                            .commit();
                }

            }
        });
    }

}


