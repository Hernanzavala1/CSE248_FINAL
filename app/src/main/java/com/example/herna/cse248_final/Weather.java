package com.example.herna.cse248_final;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;

public class Weather extends AppCompatActivity {
    // Get reference to all views in the layout
    private TextView header;
    private TextView dateText;
    private Toolbar toolbar;
    private ImageView CurrentWeatherIcon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_layout);

        CurrentWeatherIcon= findViewById(R.id.CurrentWeatherIcon);
        header = findViewById(R.id.Header);
        dateText = findViewById(R.id.dateTextfield);
        toolbar = (Toolbar) findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);
        setDate();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    public void setDate() {
        DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
        Date date = new Date();
        String date2 = df.format(date);
        dateText.setText(date2);

    }
}


