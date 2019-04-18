package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {
    private final static String LOG_TAG = EarthquakeAdapter.class.getSimpleName();
    private static final String LOCATION_SEPARATOR = " of ";

    public EarthquakeAdapter(@NonNull Context context, @NonNull List<Earthquake> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // On recupere la View en cours: c'est la racine de la View (LinearLayout)
        View listItem = convertView;

        // La View est null: on en cree une a partir du fichier xml
        // elle contient donc tous les elements du xml (les 3 TextViews)
        if(listItem == null)
            listItem = LayoutInflater.from(getContext()).
                    inflate(R.layout.earthquake_list_item, parent,false);

        // On recupere les infos "objet" stockees sur cette ligne
        final Earthquake currentEarthquake = getItem(position);

        // Et, on les affecte aux differents elements xml
        TextView magnitude_TV = (TextView) listItem.findViewById(R.id.magnitude);
        double magnitude = currentEarthquake.getMagnitude();
        DecimalFormat formatter = new DecimalFormat("0.0");
        magnitude_TV.setText(formatter.format(magnitude));


        // ************************************************

        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitude_TV.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(currentEarthquake.getMagnitude());

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);

        // ************************************************

        String location = currentEarthquake.getLocation();
        TextView offset_location_TV = (TextView) listItem.findViewById(R.id.offset_location);
        TextView primary_location_TV = (TextView) listItem.findViewById(R.id.primary_location);

        if (location != null) {
            if (location.contains(LOCATION_SEPARATOR)) {
                String[] parts = location.split(LOCATION_SEPARATOR);
                offset_location_TV.setText(parts[0] + LOCATION_SEPARATOR);
                primary_location_TV.setText(parts[1]);
            } else {
                offset_location_TV.setText(getContext().getString(R.string.near_the));
                primary_location_TV.setText(location);
            }
        } else {
            offset_location_TV.setText("");
            primary_location_TV.setText("");
        }

        TextView eventDate_TV = (TextView) listItem.findViewById(R.id.date);
        eventDate_TV.setText(currentEarthquake.getDate());

        TextView eventTime_TV = (TextView) listItem.findViewById(R.id.time);
        eventTime_TV.setText( currentEarthquake.getTime());

        return listItem;
    }

    private int getMagnitudeColor(double magnitude){
        int intMagnitude = (int) Math.floor(magnitude);

        int value;

        //Log.e(LOG_TAG, "Magnitude: '" + magnitude + "' - '" + intMagnitude + "'");

        switch (intMagnitude){
            case 0:
            case 1: value = R.color.magnitude1;
                    break;
            case 2: value = R.color.magnitude2;
                break;
            case 3: value = R.color.magnitude3;
                break;
            case 4: value = R.color.magnitude4;
                break;
            case 5: value = R.color.magnitude5;
                break;
            case 6: value = R.color.magnitude6;
                break;
            case 7: value = R.color.magnitude7;
                break;
            case 8: value = R.color.magnitude8;
                break;
            case 9: value = R.color.magnitude9;
                break;
            default: value = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), value);
    }
}
