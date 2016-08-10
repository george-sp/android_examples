package com.codeburrow.basic_transition_example;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.transition.Scene;
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

    // Empty Constructor.
    public BasicTransitionFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_basic_transition, container, false);
        assert view != null;

        mSceneRoot = (ViewGroup) view.findViewById(R.id.scene_root);

        // A Scene can be instantiated from a live view hierarchy.
        mScene1 = new Scene(mSceneRoot, (ViewGroup) mSceneRoot.findViewById(R.id.container));

        // You can also inflate a generate a Scene from a layout resource file.
        mScene2 = Scene.getSceneForLayout(mSceneRoot, R.layout.scene2, getActivity());

        // Another scene from a layout resource file.
        mScene3 = Scene.getSceneForLayout(mSceneRoot, R.layout.scene3, getActivity());

        return view;
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {

    }
}
