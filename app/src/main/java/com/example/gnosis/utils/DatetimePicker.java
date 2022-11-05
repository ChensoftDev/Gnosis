package com.example.gnosis.utils;

import android.annotation.SuppressLint;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@RequiresApi(api = Build.VERSION_CODES.O)
public class DatetimePicker {
    @RequiresApi(api = Build.VERSION_CODES.O)
    static public void show(FragmentManager fragmentManager, Listener listener) {
        openDateTimePicker(fragmentManager, listener);
    }

    public static DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a");

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static void openDateTimePicker(FragmentManager fragmentManager, Listener listener) {
        MaterialDatePicker<Long> picker = MaterialDatePicker.Builder.datePicker().build();
        picker.addOnPositiveButtonClickListener(v -> DatetimePicker.openTimePicker(v, fragmentManager, listener));
        picker.show(fragmentManager, "DatePicker");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("DefaultLocale")
    private static void openTimePicker(long epochTime, FragmentManager fragmentManager, Listener listener) {
        MaterialTimePicker materialTimePicker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .build();
        materialTimePicker.addOnPositiveButtonClickListener(view -> {
            int hour = materialTimePicker.getHour();
            int minute = materialTimePicker.getMinute();
            // convert epochTime to instance
            Instant instant = new Date(epochTime).toInstant();
            // convert instance to localDate only date
            LocalDateTime date = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
            // convert localeDateTime (date) to localDateTime (dateTime)
            LocalDateTime dateTime = LocalDateTime.of(date.getYear(),
                    date.getMonthValue(),
                    date.getDayOfMonth(),
                    hour,
                    minute
            );
            // format datetime
            String datetime = dateTime.format(dateTimeFormat);
            listener.onSuccess(datetime);
        });
        materialTimePicker.show(fragmentManager, "Time picker");
    }


    public interface Listener {
        void onSuccess(String date);
    }
}
