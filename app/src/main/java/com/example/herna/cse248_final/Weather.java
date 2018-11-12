package com.example.herna.cse248_final;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.herna.cse248_final.common.Common;
import com.example.herna.cse248_final.weatherModel.WeatherInfo;
import com.example.herna.cse248_final.weatherModel.WeatherObject;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Date;

import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Weather extends AppCompatActivity {
    // Get reference to all views in the layout
    private TextView header;
    private TextView dateText;
    private Toolbar toolbar;
    private ImageView CurrentWeatherIcon;

    private TextView hummidity;
    private TextView precipitation;
    private TextView windspeed_textview;
    private  TextView current_temp;

    private WeatherInfo  mservice;


    LocationManager locationManager;
    static final int REQUEST_LOCATION =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_layout);

        CurrentWeatherIcon= findViewById(R.id.CurrentWeatherIcon);
        header = findViewById(R.id.Header);
        dateText = findViewById(R.id.dateTextfield);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        hummidity = findViewById(R.id.hummidityAnswer);
        precipitation = findViewById(R.id.precipitation);
        windspeed_textview = findViewById(R.id.windspeed_textview);
        current_temp = findViewById(R.id.current_temp);


        setSupportActionBar(toolbar);
        setDate();
        // Location
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
       Location location =  getCurrentLocation();

       if(location != null) {
           setWeatherInformation(location);
       }


    }

    private void setWeatherInformation(Location location) {
       mservice = Common.weatherService();
       mservice.getWeatherService(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()),
               "31f21d48a88fa7b189386b5c269966f3", "metric").enqueue(new Callback<WeatherObject>() {
           @Override
           public void onResponse(Call<WeatherObject> call, Response<WeatherObject> response) {
               WeatherObject weather  = response.body();
               setAllInfo(weather);
           }

           @Override
           public void onFailure(Call<WeatherObject> call, Throwable t) {
               String message = t.getMessage().toString();
               System.out.println("the cause !!!!!!!!!! "+ message);
               Toast.makeText(Weather.this,"something went wrong", Toast.LENGTH_SHORT).show();
           }
       });


    }

    private void setAllInfo(WeatherObject weather) {
        StringBuilder builder = new StringBuilder("https://openweathermap.org/img/w/");
        builder.append(weather.getWeather().get(0).getIcon()).append(".png");
        Picasso.get().load(String.valueOf(builder)).into(CurrentWeatherIcon);
        hummidity.setText(String.valueOf(weather.main.getHumidity()));
            precipitation.setText(String.valueOf(weather.main.getPressure()));
            windspeed_textview.setText(String.valueOf(weather.wind.getSpeed()) +"mph");
            current_temp.setText(String.valueOf((int)getFahrenheit(weather.main.getTemp())) +"°");
    }

    private double getFahrenheit(double degreesKelvin) {


            double f = (((degreesKelvin - 273) * (9.0/5) ) + 32);
        DecimalFormat format = new DecimalFormat("##");
        format.format(f);
        return f;
    }

    private Location getCurrentLocation() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION} , REQUEST_LOCATION);
        }
        else{
            Location location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
           return location;
        }
        return null;
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


