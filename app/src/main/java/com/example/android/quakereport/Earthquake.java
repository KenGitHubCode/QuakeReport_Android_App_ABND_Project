package com.example.android.quakereport;

/**
 * Created by Ken Muckey on 7/6/2018.
 */
class Earthquake {

    //initialize variables
    private String myLocation;
    private String myURL;
    private long myTime;
    private double myMag;

    /**
     * Constructs a new {@link Earthquake} object.
     * @param locationInput  place of occurence
     * @param timeInput time in unixe long format
     * @param magInput mag of quake
     * @param urlInput web link
     */
    public Earthquake(String locationInput, long timeInput, double magInput, String urlInput) {
        myLocation = locationInput;
        myTime = timeInput;
        myMag = magInput;
        myURL = urlInput;
    }

    //Setters
    public void setMyLocation(String locationInput) {
        myLocation = locationInput;
    }

    public void setMyTime (long timeInput) {
        myTime = timeInput;
    }

    public void setMyMag(double magInput) {
        myMag = magInput;
    }

    public void setMyURL(String urlInput) {
        myURL = urlInput;
    }

    //Gets the first Place
    public String getMyLocation() {
        return myLocation;
    }

    //Gets the second word
    public long getMyTime () {
        return myTime;
    }

    //Gets the second word
    public double getMyMag() {
        return myMag;
    }

    public String getMyURL() {
        return myURL;
    }
}
