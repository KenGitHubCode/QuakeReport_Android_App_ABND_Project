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

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class EarthquakeActivity extends AppCompatActivity {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        // Create a fake list of earthquake locations.
        final ArrayList<Earthquake> earthquakes = new ArrayList<>(QueryUtils.extractEarthquakes());

        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        //initialize itemsAdapter using places ArrayList
        EarthquakeAdapter adapter = new EarthquakeAdapter(this, earthquakes);

        //set the adapter for listView (which is "list" view in the applicable xml) to itemsView using places
        earthquakeListView.setAdapter(adapter);

        /**
         *  Set on item click listener block
         *  Creates Variable of clicked item, assigns intent values and starts activity
         */
        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Earthquake selectedEarthquake = earthquakes.get(i);
                String earthquakeURL = selectedEarthquake.getMyURL();

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(earthquakeURL));

                // Create a new intent to open the {@link applicable activity}
                Intent myIntent = new Intent(browserIntent);

                // Start the new activity
                startActivity(myIntent);

            }
        }); // END setOnItemClickListener

    }
}
