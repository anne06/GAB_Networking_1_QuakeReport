package com.example.android.quakereport;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class Earthquake {
    private float magnitude;
    private String location;
    private LocalDate eventDate;

    public Earthquake(float magnitude, String location, LocalDate eventDate) {
        this.magnitude = magnitude;
        this.location = location;
        this.eventDate = eventDate;
    }

    public float getMagnitude() {
        return magnitude;
    }

    public String getLocation() {
        return location;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public String getStringEventDate() {
       return eventDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));
    }

    @Override
    public String toString(){
        return getMagnitude() + " - " + getLocation() + " - " + getStringEventDate();
    }
}
