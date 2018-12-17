package com.example.herna.cse248_final.views;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.herna.cse248_final.R;
import com.example.herna.cse248_final.common.Common;
import com.example.herna.cse248_final.weatherModel.WeatherInfo;
import com.example.herna.cse248_final.weatherModel.WeatherObject;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Weather extends AppCompatActivity  {
    // Get reference to all views in the layout
    private TextView header;
    private TextView dateText;
    private Toolbar toolbar;
    private ImageView CurrentWeatherIcon;

    private TextView hummidity;
    private TextView precipitation;
    private TextView windspeed_textview;
    private TextView current_temp;
    private TextView weatherDesc;
    private TextView city;

    private WeatherInfo mservice;


    LocationManager locationManager;
    public Location location;
    LocationListener listener;
    static final int REQUEST_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_layout);

        CurrentWeatherIcon = findViewById(R.id.CurrentWeatherIcon);
        header = findViewById(R.id.Header);
        dateText = findViewById(R.id.dateTextfield);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        hummidity = findViewById(R.id.hummidityAnswer);
        precipitation = findViewById(R.id.precipitation);
        windspeed_textview = findViewById(R.id.windspeed_textview);
        current_temp = findViewById(R.id.current_temp);
        weatherDesc = findViewById(R.id.weatherdesc);
        city = findViewById(R.id.city);

        setSupportActionBar(toolbar);
        setDate();


        System.out.println("just before calling on the location");

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (location != null) {
                    setDate();
                    setWeatherInformation(location);
                } else {
                    Toast.makeText(Weather.this, "Location services not available. Please turn on Location!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);

            }
        };

        System.out.println("just before calling the getLocation Method");
        getLocation();

    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 10:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                }
                break;
        }
    }

//    public void getLocation() {
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                requestPermissions(new String[]{
//                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET
//                }, 10);
//            }
//
//        }
//        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//
//    }

    public void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET
                }, 10);
            }

            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1800000, 1, (LocationListener) listener);
        System.out.println("before the request single update method!!!!!!!!!");


    }


    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("the on stop method has been called");
        locationManager.removeUpdates(this.listener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("the on Pause has been called !!!!");
        locationManager.removeUpdates(this.listener);
    }

    private void setWeatherInformation(Location location) {
        mservice = Common.weatherService();
        System.out.println("entering request body!");
        mservice.getWeatherService(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()),
                "31f21d48a88fa7b189386b5c269966f3", "metric").enqueue(new Callback<WeatherObject>() {
            @Override
            public void onResponse(Call<WeatherObject> call, Response<WeatherObject> response) {
                WeatherObject weather = response.body();
                if(weather != null) {
                    setAllInfo(weather);
                }
                else{
                    System.out.println("the object weather is null");
                    Toast.makeText(Weather.this, "The response body is Null", Toast.LENGTH_SHORT).show();
                 //   return;
                }

            }

            @Override
            public void onFailure(Call<WeatherObject> call, Throwable t) {
                String message = t.getMessage().toString();
                System.out.println("the cause !!!!!!!!!! " + message);
                Toast.makeText(Weather.this, "something went wrong", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void setAllInfo(WeatherObject weather) {
        StringBuilder builder = new StringBuilder("https://openweathermap.org/img/w/");
        builder.append(weather.getWeather().get(0).getIcon()).append(".png");
        Picasso.get().load(String.valueOf(builder)).into(CurrentWeatherIcon);
        hummidity.setText(String.valueOf(weather.main.getHumidity()) +" % ");
        precipitation.setText(String.valueOf(weather.main.getPressure())+ " mb");
        windspeed_textview.setText(String.valueOf(weather.wind.getSpeed()) + " mph");
        current_temp.setText(String.valueOf((int) getFahrenheit(weather.main.getTemp())) + "° F");
        weatherDesc.setText(weather.weather.get(0).description);
        city.setText(weather.getName());
    }

    private double getFahrenheit(double degreesKelvin) {


        double f = (((degreesKelvin - 273) * (9.0 / 5)) + 32);
        DecimalFormat format = new DecimalFormat("##");
        format.format(f);
        return f;
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
//
//    @Override
//    public void onLocationChanged(Location location) {
//        System.out.println("inside the onlocation change method!!!");
//        if (location != null) {
//
//            Toast.makeText(Weather.this,"we are getting a location", Toast.LENGTH_SHORT).show();
//            setWeatherInformation(location);
//        } else {
//            System.out.println("location is not loading !!!!!!!!!!");
//            Toast.makeText(Weather.this, "Location services not available. Please turn on Location!", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    @Override
//    public void onStatusChanged(String provider, int status, Bundle extras) {
//
//    }
//
//    @Override
//    public void onProviderEnabled(String provider) {
//
//    }
//
//    @Override
//    public void onProviderDisabled(String provider) {
//        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//        startActivity(intent);
//
//    }
}


