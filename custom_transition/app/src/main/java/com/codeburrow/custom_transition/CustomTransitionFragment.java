package com.codeburrow.custom_transition;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.transition.Scene;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class CustomTransitionFragment extends Fragment {

    private static final String LOG_TAG = CustomTransitionFragment.class.getSimpleName();

    // These are the Scenes we use.
    private Scene[] mScenes;
    // The current index for mScenes.
    private int mCurrentScene;
    private static final String STATE_CURRENT_SCENE = "current_scene";

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

        if (null != savedInstanceState) {
            mCurrentScene = savedInstanceState.getInt(STATE_CURRENT_SCENE);
        }

        // We set up the Scenes here.
        mScenes = new Scene[]{
                Scene.getSceneForLayout(container, R.layout.scene1, context),
                Scene.getSceneForLayout(container, R.layout.scene2, context),
                Scene.getSceneForLayout(container, R.layout.scene3, context),
        };

        // Show the initial Scene.
        TransitionManager.go(mScenes[mCurrentScene % mScenes.length]);
    }
}
