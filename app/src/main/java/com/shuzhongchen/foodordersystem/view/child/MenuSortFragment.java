package com.shuzhongchen.foodordersystem.view.child;

import android.app.DatePickerDialog;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.shuzhongchen.foodordersystem.R;
import com.shuzhongchen.foodordersystem.activities.AdminDashboardActivity;

import java.util.Calendar;


/**
 * Created by jinchengcheng on 5/10/18.
 */

public class MenuSortFragment extends Fragment {
    EditText startDatePicker;
    EditText endDatePicker;
    Button nextButton;

    DatePickerDialog datePickerDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewRoot = inflater.inflate(R.layout.admin_choose_date, container, false);

        startDatePicker = (EditText) viewRoot.findViewById(R.id.startDate);
        endDatePicker = (EditText) viewRoot.findViewById(R.id.endDate);
        nextButton = (Button) viewRoot.findViewById(R.id.nextButton);

        startDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                startDatePicker.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                                System.out.println("day: " + dayOfMonth + "\n");
                                System.out.println("month: " + monthOfYear + "\n");
                                System.out.println("year: " + year + "\n");


                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
                // display the values by using a toast

            }
        });


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get the values for day of month , month and year from a date picker

                //Toast.makeText(getApplicationContext(), day + "\n" + month + "\n" + year, Toast.LENGTH_LONG).show();
            }
        });
        return viewRoot;
    }
}
