package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

// We want to load a list of Earthquake objects
public class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>> {
   private final String mWebsite;

    public EarthquakeLoader(Context context, String webSite) {
        super(context);
        mWebsite = webSite;
    }

    // We override this method to call forceLoad() which is a required step
    // to actually trigger the loadInBackground() method to execute
    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Earthquake> loadInBackground() {
        if (mWebsite == null || mWebsite.length() < 1)
            return null;

        List<Earthquake> allEarthquakes = QueryUtils.extractEarthquakes(mWebsite);

        return allEarthquakes;
    }
}
