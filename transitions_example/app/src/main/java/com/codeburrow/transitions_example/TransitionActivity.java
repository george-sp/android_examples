package com.codeburrow.transitions_example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Scene;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;

public class TransitionActivity extends AppCompatActivity {

    private static final String LOG_TAG = TransitionActivity.class.getSimpleName();

    /*
     * android.transition.TransitionManager:
     * This class manages the set of transitions that fire when there is a change of
     * Scene.
     */
    private TransitionManager mTransitionManager;
    /*
     * android.transition.Scene:
     * A scene represents the collection of values that various properties
     * in the View hierarchy will have when the scene is applied.
     */
    private Scene mScene1;
    private Scene mScene2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, new TransitionFragment())
                    .commit();
        }

        ViewGroup container = (ViewGroup) findViewById(R.id.container);
        /*
         * android.transition.TransitionInflater:
         * This class inflates scenes and transitions from resource files.
         */
        // Obtain the TransitionInflater from the Activity (given context).
        TransitionInflater transitionInflater = TransitionInflater.from(this);
        // Load the TransitionManager object from a resource.
        mTransitionManager = transitionInflater.inflateTransitionManager(R.transition.transition_manager, container);
        mScene1 = Scene.getSceneForLayout(container, R.layout.scene_start, this);
        mScene2 = Scene.getSceneForLayout(container, R.layout.scene_end, this);
    }

    public void goToStartScene(View view) {
        mTransitionManager.transitionTo(mScene1);
    }

    public void goToEndScene(View view) {
        mTransitionManager.transitionTo(mScene2);
    }
}
