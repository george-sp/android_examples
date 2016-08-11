package com.codeburrow.custom_transition;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class CustomTransitionFragment extends Fragment implements View.OnClickListener {

    private static final String LOG_TAG = CustomTransitionFragment.class.getSimpleName();

    // These are the Scenes we use.
    private Scene[] mScenes;
    // The current index for mScenes.
    private int mCurrentScene;
    private static final String STATE_CURRENT_SCENE = "current_scene";
    // This is the custom Transition we use in this example.
    private Transition mTransition;

    /* Empty Constructor as per documentation. */
    public CustomTransitionFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_custom_transition, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Context context = getActivity();
        FrameLayout container = (FrameLayout) view.findViewById(R.id.container);
        // Set OnClickListener to fragment's Button.
        view.findViewById(R.id.show_next_scene_button).setOnClickListener(this);
        if (null != savedInstanceState) {
            mCurrentScene = savedInstanceState.getInt(STATE_CURRENT_SCENE);
        }

        // We set up the Scenes here.
        mScenes = new Scene[]{
                Scene.getSceneForLayout(container, R.layout.scene1, context),
                Scene.getSceneForLayout(container, R.layout.scene2, context),
                Scene.getSceneForLayout(container, R.layout.scene3, context),
        };
        // This is the custom Transition.
        mTransition = new ChangeColor();
        // Show the initial Scene.
        TransitionManager.go(mScenes[mCurrentScene % mScenes.length]);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.show_next_scene_button: {
                mCurrentScene = (mCurrentScene + 1) % mScenes.length;
                Log.e(LOG_TAG, "Transitioning to scene # " + mCurrentScene);
                // Pass the custom Transition as second argument for TransitionManager.go() method.
                TransitionManager.go(mScenes[mCurrentScene], mTransition);
                break;
            }
        }
    }
}
