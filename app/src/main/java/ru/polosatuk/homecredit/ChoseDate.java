package ru.polosatuk.homecredit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.CalendarView;

public class ChoseDate extends AppCompatActivity {
    CalendarView calendarView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chose_date);

        calendarView = (CalendarView) findViewById(R.id.calendar);
    }
}
