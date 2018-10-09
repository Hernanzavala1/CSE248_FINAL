package com.example.herna.cse248_final;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Weather extends Activity {
TextView dateText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_layout);
  dateText = findViewById(R.id.dateTextfield);
       setDate();


    }
    public void setDate(){
        DateFormat df= DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
        Date date = new Date();
        String date2 = df.format(date);
        dateText.setText(date2);

    }
}
