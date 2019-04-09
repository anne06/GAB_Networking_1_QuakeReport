package com.example.android.quakereport;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

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
                    inflate(R.layout.earthquake_list_item,parent,false);

        // On recupere les infos "objet" stockees sur cette ligne
        Earthquake currentEarthquake = getItem(position);

        // Et, on les affecte aux differents elements xml
        TextView magnitude = (TextView) listItem.findViewById(R.id.magnitude);
        magnitude.setText(String.valueOf(currentEarthquake.getMagnitude()));

        TextView location = (TextView) listItem.findViewById(R.id.location);
        location.setText(currentEarthquake.getLocation());

        TextView eventDate = (TextView) listItem.findViewById(R.id.date);
        eventDate.setText( currentEarthquake.getStringEventDate());

        return listItem;    }
}
