package com.codeburrow.custom_view_basics_example;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author George Spiridakis <george@codeburrow.com>
 * @since Jul/29/2016.
 * ===================================================
 * ---------->    http://codeburrow.com    <----------
 * ===================================================
 */
public class ShapeModifierView extends View {

    private static final String LOG_TAG = ShapeModifierView.class.getSimpleName();

    /**
     * Constructor that is called when inflating a view from XML file,
     * supplying attributes that were specified in the XML file.
     *
     * @param context The Context the view is running in, through which it can access the current theme, resources, etc.
     * @param attrs   The attributes of the XML tag that is inflating the view.
     */
    public ShapeModifierView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
