package com.example.herna.cse248_final;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.maps.MapView;

public class EventsActivity extends AppCompatActivity {

    private MapView mapView;

    // Add Event Button
    private ImageButton eventButton;
    private Button cancelButton;
    private Button saveButton;

    // layout to add event
    private RelativeLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       Mapbox.getInstance(EventsActivity.this, "pk.eyJ1IjoiemF2YWg5MSIsImEiOiJjam84NjNza2UxMmwzM3FwYmcydHdiZ2loIn0.gwLmml2xyFZOWIwlwL2phA");
        setContentView(R.layout.activity_events);

        eventButton = findViewById(R.id.eventButton);
        cancelButton = findViewById(R.id.cancelButton);
        saveButton = findViewById(R.id.saveButton);
        layout = findViewById(R.id.mainLayout);


        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

}

    //This will create an Event object and added to the list of event and display a pin on the map
    public void saveEvent(View view) {

}
    public void setCancelButton(View view){
        layout.setVisibility(View.INVISIBLE);
        eventButton.setVisibility(View.VISIBLE);
    }

    public void addEvent(View view){
        layout.setVisibility(View.VISIBLE);
        eventButton.setVisibility(View.INVISIBLE);
        mapView.setVisibility(View.INVISIBLE);
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
