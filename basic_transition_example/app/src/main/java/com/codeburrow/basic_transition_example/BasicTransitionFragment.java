package com.codeburrow.basic_transition_example;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BasicTransitionFragment extends Fragment {

    private static final String LOG_TAG = BasicTransitionFragment.class.getSimpleName();

    // Empty Constructor.
    public BasicTransitionFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_basic_transition, container, false);

        return view;
    }
}
