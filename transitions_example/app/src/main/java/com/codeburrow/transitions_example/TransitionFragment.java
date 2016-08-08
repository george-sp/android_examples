package com.codeburrow.transitions_example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.transition.Scene;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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

        // Get the Scene described by the resource file associated with the given layoutId parameter.
        final Scene endScene = Scene.getSceneForLayout(container, R.layout.scene_end, getActivity());

        Button transitionButton = (Button) rootView.findViewById(R.id.transition_button);
        transitionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Change to the given scene using the default transition for TransitionManager.
                TransitionManager.go(endScene);
            }
        });

        return rootView;
    }
}
