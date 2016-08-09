package com.codeburrow.transitions_example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.transition.ChangeBounds;
import android.transition.Fade;
import android.transition.Scene;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateOvershootInterpolator;
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
         * android.transition.ChangeBounds:
         * This transition captures the layout bounds of target views before
         * and after the scene change and animates those changes during the transition.
         */
        ChangeBounds changeBounds = new ChangeBounds();
        /*
         * android.animation.TimeInterpolator:
         * A time interpolator defines the rate of change of an animation.
         * This allows animations to have non-linear motion,
         * such as acceleration and deceleration.
         *
         * See more:
         * https://developer.android.com/reference/android/animation/TimeInterpolator.html
         */
        // Set the interpolator of this transition.
        changeBounds.setInterpolator(new AnticipateOvershootInterpolator());
        // Set the duration of this transition.
        changeBounds.setDuration(2000);
        /*
         * android.transition.Fade:
         * This transition tracks changes to the visibility of target views in the start
         * and end scenes and fades views in or out when they become visible or non-visible.
          */
        // Construct a Fade transition that will fade targets out.
        Fade fadeOut = new Fade(Fade.OUT);
        fadeOut.setDuration(1000);
        // Construct a Fade transition that will fade targets in.
        Fade fadeIn = new Fade(Fade.IN);
        fadeIn.setDuration(1000);
        /*
         * android.transition.TransitionSet:
         * A TransitionSet is a parent of child transitions (including other TransitionSets).
         * Using TransitionSets enables more complex choreography of transitions,
         * where some sets play ORDERING_TOGETHER and others play ORDERING_SEQUENTIAL
         */
        TransitionSet transitionSet = new TransitionSet();
        transitionSet.setOrdering(TransitionSet.ORDERING_SEQUENTIAL);
        transitionSet
                .addTransition(fadeOut)
                .addTransition(changeBounds)
                .addTransition(fadeIn);
        /*
         * android.transition.TransitionManager:
         * This class manages the set of transitions that fire when there is a change of
         * Scene.
         */
        TransitionManager.go(endScene, transitionSet);
    }
}
