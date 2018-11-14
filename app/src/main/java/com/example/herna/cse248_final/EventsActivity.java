package com.example.herna.cse248_final;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class EventsActivity extends AppCompatActivity {

    private MapView mapView;
    private Geocoder geocoder;

    // Add Event Button
    private ImageButton eventButton;
    private Button cancelButton;
    private Button saveButton;


    // layout to add event
    private RelativeLayout layout;
    private EditText streetAddress;
    private  EditText town;
    private  EditText state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       Mapbox.getInstance(EventsActivity.this, "pk.eyJ1IjoiemF2YWg5MSIsImEiOiJjam84NjNza2UxMmwzM3FwYmcydHdiZ2loIn0.gwLmml2xyFZOWIwlwL2phA");
        setContentView(R.layout.activity_events);

        eventButton = findViewById(R.id.eventButton);
        cancelButton = findViewById(R.id.cancelButton);
        saveButton = findViewById(R.id.saveButton);
        layout = findViewById(R.id.mainLayout);
        streetAddress = layout.findViewById(R.id.streetAddressAns);
        town = layout.findViewById(R.id.townAns);
        state = layout.findViewById(R.id.stateAns);




        String locationName ="1029 Jericho TPKE, Smithtown Ny";
        LatLng myLocation = getLocationFromAddress(locationName);
        if (myLocation != null){
            System.out.println("The latitude and logitude for my address is" +  myLocation.getLatitude()+" longitude!! "+ myLocation.getLongitude() );

        }
        else{
            System.out.println("location was null @!!!!!!!");
        }
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                MarkerOptions options = new MarkerOptions();
                options.setTitle("commack");
                options.setPosition(new LatLng(40.8428759,-73.2928943 ));
                mapboxMap.addMarker(options);
            }
        });

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                MarkerOptions options = new MarkerOptions();
                options.setTitle("smithtown");
                options.setPosition(new LatLng(40.855930, -73.200668 ));
                mapboxMap.addMarker(options);

            }
        });

}

    private void addMarker(Location location, MapboxMap map) {
        MarkerOptions options =  new MarkerOptions();
        options.setTitle("commack");
        options.setPosition(new LatLng(location.getLatitude(), location.getLongitude()));
        map.addMarker(options);

    }

    //This will create an Event object and added to the list of event and display a pin on the map
    public void saveEvent(View view) {

        if(streetAddress.getText().length() < 0 || town.getText().length() < 0 || state.getText().length() < 0){
            Toast.makeText(this, "Please enter a full address!", Toast.LENGTH_SHORT).show();
            return;
        }
        StringBuilder address = new StringBuilder();
        address.append(streetAddress.getText().toString()).append(',').append(town.getText().toString()).append(',')
                .append(state.getText().toString());
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                LatLng location = getLocationFromAddress(address.toString());
                MarkerOptions options = new MarkerOptions();
                options.setTitle("event");
                options.setPosition(location);
                mapboxMap.addMarker(options);
            }
        });
        mapView.setVisibility(View.VISIBLE);
        eventButton.setVisibility(View.VISIBLE);
        layout.setVisibility(View.INVISIBLE);

        // get adddress from all of the edit text and then some how get latitude and longitude
        // with that call map.getAsyn to create a new marker and pass in the latitude and longitude


}
    public void setCancelButton(View view){
        layout.setVisibility(View.INVISIBLE);
        eventButton.setVisibility(View.VISIBLE);
        mapView.setVisibility(View.VISIBLE);
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


    public LatLng getLocationFromAddress(String strAddress){

        Geocoder coder = new Geocoder(this);
        List<Address> address;
        LatLng p1 = null;

        try {
            address = coder.getFromLocationName(strAddress,5);
            if (address==null) {
                return null;
            }
            Address location=address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng( (location.getLatitude()),
                     (location.getLongitude()));


        } catch (IOException e) {
            e.printStackTrace();
        }
        return p1;
    }
}
