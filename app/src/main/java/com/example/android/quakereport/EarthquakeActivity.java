/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<Earthquake>> {

    /**
     * Class Variables declaration
     */
    //QA LOG_CAT for debugging
    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    //URL for earthquake data from the USGS dataset
    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";
    // Create a fake list of earthquake locations.
    private final ArrayList<Earthquake> earthquakes = new ArrayList<>();
    // Adapter for the list of earthquakes
    private EarthquakeAdapter mAdapter;
    // Constant value for the earthquake loader ID. We can choose any integer.
    // This really only comes into play if you're using multiple loaders.
    private static final int EARTHQUAKE_LOADER_ID = 1;
    //Empty Text View for no data initialize
    private TextView emptyText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);
        //identify the empty textview
        emptyText = (TextView) findViewById(R.id.empty_text_view);
        // Find a reference to the {@link ListView} in the layout
        final ListView earthquakeListView = (ListView) findViewById(R.id.list);
        earthquakeListView.setEmptyView(emptyText);
        // Create a new adapter that takes an empty list of earthquakes as input
        mAdapter = new EarthquakeAdapter(this, new ArrayList<Earthquake>());
        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(mAdapter);

        // Check Internet connectivity via Connectivity Manager
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if (isConnected) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();
            //Initiate the Loader
            loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);
        } else {
            //set the empty view text
            emptyText.setText(R.string.no_internet_text);
            // Progress view
            ProgressBar progressBar = findViewById(R.id.progress_bar);
            progressBar.setVisibility(View.GONE);
        }

        Log.i(LOG_TAG, "InitLoader just called.");

        /**
         *  Set on item click listener block
         *  Creates Variable of clicked item, assigns intent values and starts activity
         */
        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Earthquake selectedEarthquake = mAdapter.getItem(i);
                String earthquakeURL = selectedEarthquake.getMyURL();

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(earthquakeURL));

                // Create a new intent to open the {@link applicable activity}
                Intent myIntent = new Intent(browserIntent);

                // Start the new activity
                startActivity(myIntent);

            }
        }); // END setOnItemClickListener
    }

    @Override
    public Loader<List<Earthquake>> onCreateLoader(int id, Bundle args) {
        Log.i(LOG_TAG, "onCreateLoader just called.");
        return new EarthquakeLoader(this, USGS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Earthquake>> loader, List<Earthquake> loadedEarthquakes) {
        Log.i(LOG_TAG, "onLoadFinished just called.");

        // Clear the adapter of previous earthquake data
        mAdapter.clear();

        if (loadedEarthquakes != null && !loadedEarthquakes.isEmpty()) {
            mAdapter.addAll(loadedEarthquakes);
        }

        //set the empty view text
        emptyText.setText(R.string.no_data_text);

        // Progress view
        ProgressBar progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(Loader<List<Earthquake>> loader) {
        Log.e(LOG_TAG, "onLoaderReset just called.");
        // Clear the adapter of previous earthquake data
        mAdapter.clear();
    }
}
