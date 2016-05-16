package com.example.george.fragments_basic_example;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @author George Spiridakis <george@codeburrow.com>
 * @since May/16/2016.
 * ===================================================
 * ---------->    http://codeburrow.com    <----------
 * ===================================================
 */
public class TextFragment extends Fragment {

    TextView androidOs, version;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the View.
        View view = inflater.inflate(R.layout.text_fragment, container, false);
        // Get the textViews.
        androidOs = (TextView) view.findViewById(R.id.AndroidOs);
        version = (TextView) view.findViewById(R.id.Version);

        return view;
    }

    public void change(String androidOsStr, String versionStr) {
        androidOs.setText(androidOsStr);
        version.setText(versionStr);
    }
}
