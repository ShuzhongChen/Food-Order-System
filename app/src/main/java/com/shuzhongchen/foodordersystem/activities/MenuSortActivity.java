package com.shuzhongchen.foodordersystem.activities;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.shuzhongchen.foodordersystem.R;

import java.util.Calendar;

/**
 * Created by jinchengcheng on 5/14/18.
 */

public class MenuSortActivity extends AppCompatActivity {

    TextView startDatePicker;
    TextView endDatePicker;
    Button nextButton;
    Calendar newCalendar;

    DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("OnCreateView Pressed!" + "\n");

        setContentView(R.layout.admin_choose_date);
        startDatePicker = (TextView) findViewById(R.id.startDate);
        endDatePicker = (TextView) findViewById(R.id.endDate);
        nextButton = (Button) findViewById(R.id.nextButton);
        newCalendar = Calendar.getInstance();

        startDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("start date picker clicked " + "\n");

                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(MenuSortActivity.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // set day of month , month and year value in the edit text
                        System.out.println("day of month: "  + dayOfMonth + "month of year: " + monthOfYear
                         + "year: " + year + "\n");

                        startDatePicker.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

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

                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(MenuSortActivity.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        endDatePicker.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                    }
                }, mYear, mMonth, mDay);


                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
//                Calendar cal = Calendar.getInstance();
//                cal.add(Calendar.DAY_OF_WEEK, +7);
//                datePickerDialog.getDatePicker().setMaxDate(cal.getTimeInMillis());

                datePickerDialog.show();
            }

        });


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get the values for day of month , month and year from a date picker

                //Toast.makeText(getApplicationContext(), day + "\n" + month + "\n" + year, Toast.LENGTH_LONG).show();
            }
        });
    }
}
