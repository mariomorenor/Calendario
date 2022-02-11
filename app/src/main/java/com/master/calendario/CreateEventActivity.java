package com.master.calendario;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.os.Bundle;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;


public class CreateEventActivity extends AppCompatActivity {

    EditText startDate;
    EditText endDate;
    Button btnBack ;
    Button btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        startDate = (EditText) findViewById(R.id.etStartDate);
        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate(v);
            }
        });

        endDate = (EditText) findViewById(R.id.etEndDate);
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate(v);
            }
        });

        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnConfirm = (Button) findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent calIntent = new Intent(Intent.ACTION_INSERT);
                calIntent.setData(CalendarContract.Events.CONTENT_URI);
                startActivity(calIntent);
            }
        });


    }

    public void setDate(View v){
        switch (v.getId()){
            case R.id.etStartDate:
                showCalendar(v, startDate);
                break;
            case R.id.etEndDate:
                showCalendar(v,endDate);
                break;
        }
    }

    public void showCalendar(View v,EditText txtDate){
        final Calendar currentDate = Calendar.getInstance(TimeZone.getTimeZone("GMT-5"));
        DatePickerDialog datePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                TimePickerDialog timePicker = new TimePickerDialog(CreateEventActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        SimpleDateFormat tf = new SimpleDateFormat("dd/MM/y HH:mm");
                        Calendar selectedDate = Calendar.getInstance();
                        selectedDate.set(year,month,dayOfMonth,hourOfDay,minute);

                        txtDate.setText(tf.format(selectedDate.getTime()));
                    }
                },currentDate.get(Calendar.HOUR_OF_DAY),currentDate.get(Calendar.MINUTE),false);
                timePicker.show();
            }
        },currentDate.get(Calendar.YEAR),currentDate.get(Calendar.MONTH),currentDate.get(Calendar.DAY_OF_MONTH));
        datePicker.show();
    }

}