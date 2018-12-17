package com.example.herna.cse248_final.views;

import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.herna.cse248_final.R;
import com.example.herna.cse248_final.eventModel.Event;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EventsActivity extends AppCompatActivity  implements  MapboxMap.OnMarkerClickListener{

    private MapView mapView;
    private Geocoder geocoder;

    // Add Event Button
    private ImageButton eventButton;
    private Button cancelButton;
    private Button saveButton;
    private Button deleteButton;


    // layout to add event
    private EditText eventName;
    private EditText eventDate;
    private EditText eventDescription;
    private RelativeLayout layout;
    private EditText streetAddress;
    private  EditText town;
    private  EditText state;

    private DatabaseReference myRef;

    private ArrayList<Event> list ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Mapbox.getInstance(EventsActivity.this, "pk.eyJ1IjoiemF2YWg5MSIsImEiOiJjam84NjNza2UxMmwzM3FwYmcydHdiZ2loIn0.gwLmml2xyFZOWIwlwL2phA");
        setContentView(R.layout.activity_events);
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);




        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Events");


        list = new ArrayList<>();



        eventButton = findViewById(R.id.eventButton);
        cancelButton = findViewById(R.id.cancelButton);
        saveButton = findViewById(R.id.saveButton);
        deleteButton = findViewById(R.id.DeleteButton);
        layout = findViewById(R.id.mainLayout);
        eventName = layout.findViewById(R.id.eventNameAnswer);
        eventDate = layout.findViewById(R.id.eventDateAns);
        eventDescription = layout.findViewById(R.id.eventDescriptionAns);
        streetAddress = layout.findViewById(R.id.streetAddressAns);
        town = layout.findViewById(R.id.townAns);
        state = layout.findViewById(R.id.stateAns);



    }

    private void findEvent(Marker marker) {

        Event eventFound = null;

        for(Event event : list){
            LatLng eventLocation = new LatLng();
            eventLocation.setLatitude(event.getLatitude());
            eventLocation.setLongitude(event.getLongitude());

            if( eventLocation.getLongitude() == marker.getPosition().getLongitude() &&
                    eventLocation.getLatitude() == marker.getPosition().getLatitude()){

                eventFound = event;
                break;
            }
        }
        if( eventFound == null){
            Toast.makeText(EventsActivity.this, "Event is not found! Error!", Toast.LENGTH_SHORT).show();
            return;
        }
        displayEvent(eventFound, marker);

    }

    private void displayEvent(Event eventFound, Marker marker) {
        deleteButton.setEnabled(true);
        saveButton.setEnabled(false);

        mapView.setVisibility(View.INVISIBLE);
        eventButton.setVisibility(View.INVISIBLE);
        layout.setVisibility(View.VISIBLE);

        eventName.setText(eventFound.getEventName());
        eventDate.setText(eventFound.getEventDate());
        eventDescription.setText(eventFound.getEventDescription());
        LatLng latLng = new LatLng();
        latLng.setLatitude(eventFound.getLatitude());
        latLng.setLongitude(eventFound.getLongitude());
        Address address = getAddress(latLng);
        if(address != null) {
            String[] fullAddress = address.toString().split(",");
            String[] streetAddress2 = fullAddress[0].split(":");
            streetAddress.setText(streetAddress2[1].substring(1));
            town.setText(fullAddress[1]);
            state.setText(fullAddress[2].substring(0,3));
        }

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteEvent(eventFound, marker);
            }
        });
    }

    public Address getAddress(LatLng latLng){
        Geocoder coder = new Geocoder(this);
        List<Address> address;
        Address location=null;
        LatLng p1 = latLng;

        try {
            address = coder.getFromLocation(p1.getLatitude(),p1.getLongitude(), 5);
            if (address==null) {
                return null;
            }
            location =address.get(0);



        } catch (IOException e) {
            e.printStackTrace();
        }
        return location ;

    }
    private void deleteEvent(Event eventFound, Marker marker) {
        list.remove(eventFound);
        myRef.child(eventFound.getId()).removeValue();
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                mapboxMap.removeMarker(marker);
            }
        });

        // update();

        Toast.makeText(this, "event deleted!", Toast.LENGTH_SHORT).show();
        deleteButton.setEnabled(false);
        layout.setVisibility(View.INVISIBLE);
        mapView.setVisibility(View.VISIBLE);
        eventButton.setVisibility(View.VISIBLE);
        cleanAllFields();
    }


    //This will create an Event object and added to the list of event and display a pin on the map
    public void saveEvent(View view) {

        deleteButton.setEnabled(false);
        //  saveButton.setEnabled(true);

        if(streetAddress.getText().length() < 0 || town.getText().length() < 0 || state.getText().length() < 0){
            Toast.makeText(this, "Please enter a full address!", Toast.LENGTH_SHORT).show();
            return;
        }
        Event event = new Event();
        event.setEventName(eventName.getText().toString());
        event.setEventDate(eventDate.getText().toString());
        event.setEventDescription(eventDescription.getText().toString());

        StringBuilder address = new StringBuilder();

        address.append(streetAddress.getText().toString()).append(',').append(town.getText().toString()).append(',')
                .append(state.getText().toString());

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                LatLng location = getLocationFromAddress(address.toString());
                MarkerOptions options = new MarkerOptions();
                options.setTitle("event");

                event.setLatitude(location.getLatitude());
                event.setLongitude(location.getLongitude());

                String id =   myRef.push().getKey();

                event.setId(id);

                myRef.child(id).setValue(event);
                Toast.makeText(EventsActivity.this, "event added to db!", Toast.LENGTH_SHORT).show();

                options.setPosition(location);
                mapboxMap.addMarker(options);
            }
        });
        list.add(event);

        mapView.setVisibility(View.VISIBLE);
        eventButton.setVisibility(View.VISIBLE);
        layout.setVisibility(View.INVISIBLE);

        cleanAllFields();



    }

    public void setCancelButton(View view){
        layout.setVisibility(View.INVISIBLE);
        eventButton.setVisibility(View.VISIBLE);
        mapView.setVisibility(View.VISIBLE);
    }

    public void addEvent(View view){
        saveButton.setEnabled(true);
        cleanAllFields();
        layout.setVisibility(View.VISIBLE);
        eventButton.setVisibility(View.INVISIBLE);
        mapView.setVisibility(View.INVISIBLE);
    }

    public void cleanAllFields(){
        eventName.getText().clear();
        eventDate.getText().clear();
        eventDescription.getText().clear();
        streetAddress.getText().clear();
        town.getText().clear();
        state.getText().clear();
    }




    public void update(){

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();

                for(DataSnapshot Event: dataSnapshot.getChildren()){
                    Event event = Event.getValue(Event.class);
                    list.add(event);
                }

                for(Event i: list){
                    mapView.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(MapboxMap mapboxMap) {
                            MarkerOptions options = new MarkerOptions();
                            options.setTitle(i.getEventName());
                            options.setPosition(new LatLng(i.getLatitude(),i.getLongitude()));
                            mapboxMap.addMarker(options);


                        }
                    });
                }
                Toast.makeText(EventsActivity.this, "Map Updated!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();

                for(DataSnapshot Event: dataSnapshot.getChildren()){
                    Event event = Event.getValue(Event.class);
                    list.add(event);
                }

                for(Event i: list){
                    mapView.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(MapboxMap mapboxMap) {
                            mapboxMap.setOnMarkerClickListener(EventsActivity.this);
                            MarkerOptions options = new MarkerOptions();
                            options.setTitle(i.getEventName());
                            options.setPosition(new LatLng(i.getLatitude(),i.getLongitude()));
                            mapboxMap.addMarker(options);
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        findEvent(marker);
        return true;
    }
}