package com.shuzhongchen.foodordersystem.helper;

import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by jinchengcheng on 5/9/18.
 */


public class RangeTimePickerDialog extends TimePickerDialog {

        private int minHour, minMinute, maxHour, maxMinute;

        private int currentHour, currentMinute;

        public RangeTimePickerDialog(Context context, OnTimeSetListener callBack, int hourOfDay, int minute, boolean is24HourView) {
            super(context, callBack, hourOfDay, minute, is24HourView);
        }

        public void setMin(int hour, int minute) {
            minHour = hour;
            minMinute = minute;
        }

        public void setMax(int hour, int minute) {
            maxHour = hour;
            maxMinute = minute;
        }

        @Override
        public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
            super.onTimeChanged(view, hourOfDay, minute);

            boolean validTime;
            if(hourOfDay < minHour || hourOfDay > maxHour) {
                validTime = false;
            } else if(hourOfDay == minHour) {
                validTime = minute >= minMinute;
            } else if(hourOfDay == maxHour) {
                validTime = minute <= maxMinute;
            } else {
                validTime = true;
            }

            if(validTime) {
                currentHour = hourOfDay;
                currentMinute = minute;
            } else {
                Toast.makeText(getContext(), "please choose time between 6:00am - 9:00pm", Toast.LENGTH_LONG);
                updateTime(0, 0);
            }
        }

}
