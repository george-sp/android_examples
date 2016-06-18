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

    /**
     * What the ViewHolder class does is cache the call to findViewById().
     * <p/>
     * Once your ListView has reached the max amount of rows it can display on a screen,
     * Android is smart enough to begin recycling those row Views.
     * <p/>
     * We check if a View is recycled with if (convertView == null).
     * If it is not null then we have a recycled View and can just change its values,
     * otherwise we need to create a new row View.
     * <p/>
     * The magic behind this is the setTag() method
     * which lets us attach an arbitrary object onto a View object,
     * which is how we save the already inflated View for future reuse.
     */
    private static class ViewHolder {

        TextView name;
        TextView data;
    }

    public FileArrayAdapter(Context context, ArrayList<Properties> propertiesArrayList) {
        super(context, 0, propertiesArrayList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position.
        Properties properties = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view.
        ViewHolder viewHolder; // View lookup cache stored it tag.
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item_file, parent, false);
            viewHolder.name = (TextView) convertView.findViewById(R.id.name_property_textview);
            viewHolder.data = (TextView) convertView.findViewById(R.id.data_property_textview);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Populate the data into the template view using the data object.
        viewHolder.name.setText(properties.getName());
        viewHolder.data.setText(properties.getData());
        // Return the completed view to render on screen.
        return convertView;
    }
}
