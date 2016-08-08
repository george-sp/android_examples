package com.codeburrow.transitions_example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TransitionFragment extends Fragment {

    private static final String LOG_TAG = TransitionFragment.class.getSimpleName();

    /**
     * Empty Constructor.
     */
    public TransitionFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.scene_start, container, false);
        return rootView;
    }
}
