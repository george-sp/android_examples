package com.codeburrow.basic_transition_example;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.transition.Scene;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

public class BasicTransitionFragment extends Fragment implements RadioGroup.OnCheckedChangeListener {

    private static final String LOG_TAG = BasicTransitionFragment.class.getSimpleName();

    // We transition between these Scenes
    private Scene mScene1;
    private Scene mScene2;
    private Scene mScene3;
    /**
     * Transitions take place in this ViewGroup. We retain this for the dynamic transition on scene 4.
     */
    private ViewGroup mSceneRoot;
    /**
     * A custom TransitionManager
     */
    private TransitionManager mTransitionManagerForScene3;


    // Empty Constructor.
    public BasicTransitionFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_basic_transition, container, false);
        assert view != null;
        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.select_scene);
        radioGroup.setOnCheckedChangeListener(this);

        mSceneRoot = (ViewGroup) view.findViewById(R.id.scene_root);

        // A Scene can be instantiated from a live view hierarchy.
        mScene1 = new Scene(mSceneRoot, (ViewGroup) mSceneRoot.findViewById(R.id.container));

        // You can also inflate a generate a Scene from a layout resource file.
        mScene2 = Scene.getSceneForLayout(mSceneRoot, R.layout.scene2, getActivity());

        // Another scene from a layout resource file.
        mScene3 = Scene.getSceneForLayout(mSceneRoot, R.layout.scene3, getActivity());

        // We create a custom TransitionManager for Scene 3, in which ChangeBounds and Fade
        // take place at the same time.
        mTransitionManagerForScene3 = TransitionInflater.from(getActivity())
                .inflateTransitionManager(R.transition.scene3_transition_manager, mSceneRoot);

        return view;
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        switch (checkedId) {
            case R.id.select_scene_1: {
                // You can start an automatic transition with TransitionManager.go().
                TransitionManager.go(mScene1);
                break;
            }
            case R.id.select_scene_2: {
                TransitionManager.go(mScene2);
                break;
            }
            case R.id.select_scene_3: {
                // You can also start a transition with a custom TransitionManager.
                mTransitionManagerForScene3.transitionTo(mScene3);
                break;
            }
        }
    }
}
