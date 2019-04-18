package com.example.android.quakereport;

import java.text.SimpleDateFormat;

public class Earthquake {
    private double magnitude;
    private String location;
    private long mTimeInMilliseconds;
    private String webSiteUrl;

    public Earthquake(double magnitude, String location, long eventDate, String webSiteUrl) {
        this.magnitude = magnitude;
        this.location = location;
        this.mTimeInMilliseconds = eventDate;
        this.webSiteUrl = webSiteUrl;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public String getLocation() {
        return location;
    }

    public long getTimeInMilliseconds() { return mTimeInMilliseconds;}

    public String getWebSiteUrl() { return webSiteUrl; }

    public String  getTime() {
        // HH:mm AM
        SimpleDateFormat dateFormatter = new SimpleDateFormat("H:mm a");
        return dateFormatter.format(mTimeInMilliseconds);
    }

    public String getDate() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM d, yyy");
        return dateFormatter.format(mTimeInMilliseconds);
    }

    @Override
    public String toString(){
        return getMagnitude() + " - " + getLocation() + " - " +
                getDate() + " - " + getTime() + "\n" + getWebSiteUrl();
    }
}
