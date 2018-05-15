package com.shuzhongchen.foodordersystem.activities;

import android.app.DatePickerDialog;
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

        datePickerDialog = new DatePickerDialog(this.getApplicationContext(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));



        startDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("start date picker clicked " + "\n");
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
