package com.codeburrow.file_chooser_example;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * @author George Spiridakis <george@codeburrow.com>
 * @since Jun/18/2016.
 * ===================================================
 * ---------->    http://codeburrow.com    <----------
 * ===================================================
 */
public class FileArrayAdapter extends ArrayAdapter<Properties> {

    private static final String LOG_TAG = FileArrayAdapter.class.getSimpleName();


    public FileArrayAdapter(Context context, ArrayList<Properties> propertiesArrayList) {
        super(context, 0, propertiesArrayList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Properties properties = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_file, parent, false);
        }
        // Lookup view for data population
        TextView nameTextView = (TextView) convertView.findViewById(R.id.name_property_textview);
        TextView dateTextView = (TextView) convertView.findViewById(R.id.data_property_textview);
        // Populate the data into the template view using the data object
        nameTextView.setText(properties.getName());
        dateTextView.setText(properties.getData());
        // Return the completed view to render on screen
        return convertView;
    }
}
