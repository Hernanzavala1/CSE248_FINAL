package com.example.herna.cse248_final;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.maps.MapView;

public class EventsActivity extends AppCompatActivity {

    private MapView mapView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       Mapbox.getInstance(EventsActivity.this, "pk.eyJ1IjoiemF2YWg5MSIsImEiOiJjam84NjNza2UxMmwzM3FwYmcydHdiZ2loIn0.gwLmml2xyFZOWIwlwL2phA");
        setContentView(R.layout.activity_events);
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
}
    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
}
