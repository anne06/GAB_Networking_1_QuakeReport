package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

// We want to load a list of Earthquake objects
public class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>> {
   private final String mWebsite;
   private static final String LOG_TAG = EarthquakeLoader.class.getSimpleName();

    public EarthquakeLoader(Context context, String webSite) {
        super(context);
        mWebsite = webSite;
    }

    // We override this method to call forceLoad() which is a required step
    // to actually trigger the loadInBackground() method to execute
    @Override
    protected void onStartLoading() {
        Log.e(LOG_TAG, "TEST: onCreate()");

        forceLoad();
    }

    @Override
    public List<Earthquake> loadInBackground() {
        Log.e(LOG_TAG, "TEST: loadInBackgroung()");

        /* Used to test the loading indicator
        try
        {
            Thread.sleep(3000);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
        */

        if (mWebsite == null || mWebsite.length() < 1)
            return null;

        List<Earthquake> allEarthquakes = QueryUtils.extractEarthquakes(mWebsite);

        return allEarthquakes;
    }
}
