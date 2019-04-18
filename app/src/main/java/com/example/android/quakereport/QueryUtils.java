package com.example.android.quakereport;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

     /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Return a list of {@link Earthquake} objects that has been built up from
     * parsing a JSON response.
     */
    public static List<Earthquake> extractEarthquakes(String webQuery) {
        Log.e(LOG_TAG, "TEST: extractEarthquakes()");

        // The first step is to create an url
        URL url = createURL(webQuery);
        String jsonResponse = makeHttpRequest(url);
        List<Earthquake> earthquakes = parseJSON(jsonResponse);

        // Return the list of earthquakes
        return earthquakes;

    }

    private static URL createURL(String webSiteURL) {

        URL url = null;
        try {
            url = new URL(webSiteURL);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }

        return url;
    }

    private static String makeHttpRequest(URL url) {
        String jsonResponse = "";

        if (url == null)
            return jsonResponse;

        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;

        try {
            // Establishment of the HTTP connection on the webserver
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(10000 /* milliseconds */);
            httpURLConnection.setConnectTimeout(15000 /* milliseconds */);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (httpURLConnection.getResponseCode() == 200) {
                inputStream = httpURLConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + httpURLConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) {
        if (inputStream == null)
            return null;

        StringBuilder jsonResponse = new StringBuilder();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
        BufferedReader reader = new BufferedReader(inputStreamReader);
        try {
            String line = reader.readLine();
            while (line != null) {
                jsonResponse.append(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error in network reading" + e);
            return null;
        }

        return jsonResponse.toString();
    }

    private static List<Earthquake> parseJSON(String jsonResponse) {
        List<Earthquake> allEarthquakes = new ArrayList();

        // Try to parse the jsonResponse. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            if (jsonResponse != null) {
                // Creation of the ROOT JSON object
                JSONObject baseJsonResponse = new JSONObject(jsonResponse);

                // Creation of the "features" JSON array
                JSONArray earthquakeArray = baseJsonResponse.getJSONArray("features");

                for (int i = 0; i < earthquakeArray.length(); i++) {
                    // On recupere les donnees du tableau comme etant un JSONObject !!!
                    JSONObject currentEarthquake = earthquakeArray.getJSONObject(i);

                    // Pour cette ligne, on recupere l'objet "properties"
                    JSONObject properties = currentEarthquake.getJSONObject("properties");

                    double magnitude = properties.getDouble("mag");
                    String location = properties.getString("place");
                    long time = properties.getLong("time");
                    String url = properties.getString("url");

                    //Log.e(LOG_TAG, "TimeStamp: " + time);

                    Earthquake earthquake = new Earthquake(magnitude, location, time, url);
                    allEarthquakes.add(earthquake);
                }


            } else {
                Log.e(LOG_TAG, "The source String is null");
            }
        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e(LOG_TAG, "Problem parsing the earthquake JSON results", e);
        }
        return allEarthquakes;
    }
}
