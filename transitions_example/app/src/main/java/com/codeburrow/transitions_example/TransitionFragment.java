package com.codeburrow.transitions_example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionInflater;
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
        View endView = inflater.inflate(R.layout.scene_end, container, false);

        /*
         * android.transition.Scene:
         * A scene represents the collection of values that various properties
         * in the View hierarchy will have when the scene is applied.
         */
        final Scene startScene = new Scene(container, (ViewGroup) rootView);
        final Scene endScene = new Scene(container, (ViewGroup) endView);

        Button transitionButton = (Button) rootView.findViewById(R.id.transition_button);
        transitionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToScene(endScene);
            }
        });

        Button reverseTransitionButton = (Button) endView.findViewById(R.id.transition_button);
        reverseTransitionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToScene(startScene);
            }
        });

        return rootView;
    }

    /**
     * Helper Method.
     * <p/>
     * Changes to the given Scene using custom transitions.
     *
     * @param endScene The Scene to change to.
     */
    private void goToScene(Scene endScene) {
        /*
         * android.transition.TransitionInflater:
         * This class inflates scenes and transitions from resource files.
         */
        // Obtain the TransitionInflater from the Activity (given context).
        TransitionInflater transitionInflater = TransitionInflater.from(getActivity());
        /*
         * android.transition.Transition:
         * A Transition holds information about animations that will be run on its targets during a scene change.
         */
        // Load the Transition object from a resource.
        Transition transition = transitionInflater.inflateTransition(R.transition.custom_transition);
        /*
         * android.transition.TransitionManager:
         * This class manages the set of transitions that fire when there is a change of
         * Scene.
         */
        TransitionManager.go(endScene, transition);
    }
}
