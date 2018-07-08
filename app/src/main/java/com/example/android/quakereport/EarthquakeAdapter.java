package com.example.android.quakereport;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Ken Muckey on 7/6/2018.
 */
class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    /**
     * This is our own custom constructor (it doesn't mirror a superclass constructor).
     * The context is used to inflate the layout file, and the list is the data we want
     * to populate into the lists.
     *
     * @param context The current context. Used to inflate the layout file.
     * @param places  A List of Earthquake objects to display in a list
     */
    public EarthquakeAdapter(Activity context, ArrayList<Earthquake> places) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, places);
    }

    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position    The position in the list of data that should be displayed in the
     *                    list item view.
     * @param convertView The recycled view to populate.
     * @param parent      The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Get the {@link Earthquake} object located at this position in the list
        Earthquake currentEarthquake = getItem(position);

        /*  Mag in place list item***********************************/
        // Find the TextView in the list_item.xml layout with the ID version_name
        TextView magnitudeView = listItemView.findViewById(R.id.list_item_mag);

        /*  Mag - Set the proper background color on the magnitude circle. ********************/
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeView.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(currentEarthquake.getMyMag());

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);

        // Get the version name from the current Earthquake object and set this text on the name TextView
        DecimalFormat formatter = new DecimalFormat("0.0");
        String magFormatted = formatter.format(currentEarthquake.getMyMag());
        magnitudeView.setText(magFormatted);


        /* Location in place list item ***********************************/
        // Find the TextView in the list_item.xml layout with the ID version_number
        TextView placeDistanceFromTextView = listItemView.findViewById(R.id.list_item_loc_dist);
        TextView placeCityTextView = listItemView.findViewById(R.id.list_item_loc_city);

        //Parse the input into two seperate variables to display
        String placeDistanceFrom = parsePlaceDist(currentEarthquake.getMyLocation());
        String placeCity = parsePlaceCity(currentEarthquake.getMyLocation());

        // Get the version number from the current Earthquake object and set this text on the number TextView
        placeDistanceFromTextView.setText(placeDistanceFrom);
        placeCityTextView.setText(placeCity);


        /* Date and Time in place list item ***********************************/
        // Create a new Date object from the time in milliseconds of the earthquake
        Date dateObject = new Date(currentEarthquake.getMyTime());
        // Find the TextView with view ID date
        TextView dateView = (TextView) listItemView.findViewById(R.id.list_item_date);
        // Format the date string (i.e. "Mar 3, 1984")
        String formattedDate = formatDate(dateObject);
        // Display the date of the current earthquake in that TextView
        dateView.setText(formattedDate);

        //TIME
        // Find the TextView with view ID time
        TextView timeView = (TextView) listItemView.findViewById(R.id.list_item_time);
        // Format the time string (i.e. "4:30PM")
        String formattedTime = formatTime(dateObject);
        // Get the version number from the current Earthquake object and set this text on the number TextView
        timeView.setText(formattedTime);

        // Return the whole list item layout ***********************************/
        return listItemView;
    }

    /**
     * Determine mag color based on mag number
     *
     * @param myMag magnitude input
     * @return int color reference
     */
    private int getMagnitudeColor(double myMag) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(myMag);
        switch ((int) magnitudeFloor) {
            case (0):
                return ContextCompat.getColor(getContext(), R.color.magnitude1);
            case (1):
                return ContextCompat.getColor(getContext(), R.color.magnitude1);
            case (2):
                return ContextCompat.getColor(getContext(), R.color.magnitude2);
            case (3):
                return ContextCompat.getColor(getContext(), R.color.magnitude3);
            case (4):
                return ContextCompat.getColor(getContext(), R.color.magnitude4);
            case (5):
                return ContextCompat.getColor(getContext(), R.color.magnitude5);
            case (6):
                return ContextCompat.getColor(getContext(), R.color.magnitude6);
            case (7):
                return ContextCompat.getColor(getContext(), R.color.magnitude7);
            case (8):
                return ContextCompat.getColor(getContext(), R.color.magnitude8);
            case (9):
                return ContextCompat.getColor(getContext(), R.color.magnitude9);
            default:
                return ContextCompat.getColor(getContext(), R.color.magnitude10plus);
        }

    }

    private String parsePlaceDist(String myLocation) {

        String myDistance;
        int findMyComma = myLocation.indexOf(",");
        int findMyOf = myLocation.indexOf(" of ");
        // if comma is found parse, if not then return "Near the" string
        if (findMyComma != -1) {
            myDistance = myLocation.substring(0, findMyOf + 4);
        } else {
            myDistance = "Near the";
        }
        return myDistance;
    }

    private String parsePlaceCity(String myLocation) {

        String myDistance;
        int findMyComma = myLocation.indexOf(",");
        int findMyOf = myLocation.indexOf(" of ");
        // if comma is found parse, if not then return blank string
        if (findMyComma != -1) {
            myDistance = myLocation.substring(findMyOf + 4, myLocation.length());
        } else {
            myDistance = myLocation;
        }
        return myDistance;
    }

    /**
     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
     */
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

}

