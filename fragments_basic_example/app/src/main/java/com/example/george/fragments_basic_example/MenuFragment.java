package com.example.george.fragments_basic_example;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * @author George Spiridakis <george@codeburrow.com>
 * @since May/16/2016.
 * ===================================================
 * ---------->    http://codeburrow.com    <----------
 * ===================================================
 */
public class MenuFragment extends ListFragment {

    private static final String[] ANDROID_OS_LIST = new String[]{
            "Cupcake",
            "Donut",
            "Eclair",
            "Froyo",
            "Gingerbread",
            "Honeycomb",
            "Ice Cream SandWich",
            "Jelly Bean",
            "KitKat",
            "Lollipop",
            "Marshmallow",
            "N"};
    private static final String[] VERSION_LIST = new String[]{
            "1.5",
            "1.6",
            "2.0-2.1",
            "2.2",
            "2.3",
            "3.0-3.2",
            "4.0",
            "4.1-4.3",
            "4.4",
            "5.0-5.1",
            "6.0-6.1",
            "Developer Preview 2"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the view.
        View view =inflater.inflate(R.layout.list_fragment, container, false);
        // Create an Array Adapter.
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, ANDROID_OS_LIST);
        // Set it to the list fragment.
        setListAdapter(adapter);

        return view;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        TextFragment txt = (TextFragment)getFragmentManager().findFragmentById(R.id.text_fragment);
        txt.change(ANDROID_OS_LIST[position],"Version : "+ VERSION_LIST[position]);
        getListView().setSelector(android.R.color.holo_blue_dark);
    }
}