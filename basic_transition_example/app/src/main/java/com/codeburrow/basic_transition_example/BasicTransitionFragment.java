package com.codeburrow.basic_transition_example;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

public class BasicTransitionFragment extends Fragment implements RadioGroup.OnCheckedChangeListener {

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

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {

    }
}
