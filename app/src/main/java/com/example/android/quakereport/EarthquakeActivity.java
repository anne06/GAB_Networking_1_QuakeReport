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

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        // Create a fake list of earthquake locations.
        List<Earthquake> earthquakes = new ArrayList<>();
        earthquakes.add(new Earthquake(7.2f,"San Francisco", LocalDate.of(2016,2,2)));
        earthquakes.add(new Earthquake(6.1f,"London", LocalDate.of(2015,7,20)));
        earthquakes.add(new Earthquake(3.9f,"Tokyo", LocalDate.of(2014,11,10)));
        earthquakes.add(new Earthquake(5.4f,"Mexico City", LocalDate.of(2014,5,3)));
        earthquakes.add(new Earthquake(2.8f,"Moscow", LocalDate.of(2013,1,2)));
        earthquakes.add(new Earthquake(4.9f,"Rio de Janeiro", LocalDate.of(2012,8,19)));
        earthquakes.add(new Earthquake(1.6f,"Paris", LocalDate.of(2011,10,30)));

        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.listEarthquake);

        // Create a new {@link ArrayAdapter} of earthquakes
        EarthquakeAdapter adapter = new EarthquakeAdapter(this, earthquakes);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(adapter);

        for (Earthquake event: earthquakes) {
            Log.d(LOG_TAG, event.toString());
        }
    }
}
