package com.codeburrow.custom_transition;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CustomTransitionFragment extends Fragment {

    private static final String LOG_TAG = CustomTransitionFragment.class.getSimpleName();

    /* Empty Constructor as per documentation. */
    public CustomTransitionFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_custom_transition, container, false);
    }
}
