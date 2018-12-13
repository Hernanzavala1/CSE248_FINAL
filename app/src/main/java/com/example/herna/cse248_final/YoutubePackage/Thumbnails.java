package com.example.herna.cse248_final.YoutubePackage;

public class Thumbnails {
    public Default aDefault;
    public Medium medium ;
    public High high ;
    public Standard standard ;
    public Maxres maxres ;

    public Medium getMedium() {
        return medium;
    }

    public Default getaDefault() {
        return aDefault;
    }

    public void setaDefault(Default aDefault) {
        this.aDefault = aDefault;
    }

    public void setMedium(Medium medium) {
        this.medium = medium;
    }

    public High getHigh() {
        return high;
    }

    public void setHigh(High high) {
        this.high = high;
    }

    public Standard getStandard() {
        return standard;
    }

    public void setStandard(Standard standard) {
        this.standard = standard;
    }

    public Maxres getMaxres() {
        return maxres;
    }

    public void setMaxres(Maxres maxres) {
        this.maxres = maxres;
    }
}
