package com.example.herna.cse248_final.eventModel;

public class Address {
    private String streetName;
    private String StreetNumber;
    private String town;
    private String State;

    public Address(String streetName, String streetNumber, String town, String state) {
        this.streetName = streetName;
        StreetNumber = streetNumber;
        this.town = town;
        State = state;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getStreetNumber() {
        return StreetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        StreetNumber = streetNumber;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }
}
