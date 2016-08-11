package com.codeburrow.custom_transition;

import android.transition.Transition;
import android.transition.TransitionValues;

/**
 * A Custom Transition enables you to create animations that are not available
 * from any of the built-in transition classes.
 */
public class ChangeColor extends Transition {

    private static final String LOG_TAG = ChangeColor.class.getSimpleName();

    /**
     * Key to store a color value in TransitionValues object
     */
    private static final String PROPNAME_BACKGROUND = "customtransition:change_color:background";

    /**
     * Convenience method: Add the background Drawable property value
     * to the TransitionsValues.value Map for a target.
     */
    private void captureValues(TransitionValues values) {
        // Capture the property values of views for later use.
        values.values.put(PROPNAME_BACKGROUND, values.view.getBackground());
    }

    /**
     * Captures the values in the start scene for the properties
     * that this transition monitors.
     *
     * @param transitionValues The holder for any values that the Transition wishes to store.
     */
    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    /**
     * Captures the values in the end scene for the properties
     * that this transition monitors.
     *
     * @param transitionValues The holder for any values that the Transition wishes to store.
     */
    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }
}
