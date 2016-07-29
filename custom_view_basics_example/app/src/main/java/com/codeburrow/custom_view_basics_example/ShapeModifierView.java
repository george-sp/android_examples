package com.codeburrow.custom_view_basics_example;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
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

    private Context mContext;

    private int mShapeColor;
    private String mShapeName;
    private boolean mDisplayShapeName;

    /**
     * Constructor that is called when inflating a view from XML file,
     * supplying attributes that were specified in the XML file.
     *
     * @param context The Context the view is running in, through which it can access the current theme, resources, etc.
     * @param attrs   The attributes of the XML tag that is inflating the view.
     */
    public ShapeModifierView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context;
        retrieveCustomAttributes(attrs);
    }

    /**
     * Retrieve the Custom Attributes from the XML file.
     *
     * @param attrs The desired attributes to be retrieved.
     */
    private void retrieveCustomAttributes(AttributeSet attrs) {
        /*
         * TypedArray: Container for an array of values that were retrieved with obtainStyledAttributes(...).
         * Be sure to call recycle() when done with them.
         *
         * obtainStyledAttributes: Returns a TypedArray holding an array of the attribute values.
         */
        // Obtain a TypedArray of the attribute values.
        TypedArray attrsTypedArray = mContext.getTheme().obtainStyledAttributes(attrs, R.styleable.ShapeModifierView, 0, 0);
        try {
            // Retrieve the color value for the attribute at index: ShapeModifierView_shapeColor.
            mShapeColor = attrsTypedArray.getColor(R.styleable.ShapeModifierView_shapeColor, Color.BLACK);
            // Retrieve the string value for the attribute at index: ShapeModifierView_shapeName.
            mShapeName = attrsTypedArray.getString(R.styleable.ShapeModifierView_shapeName);
            // Retrieve the boolean value for the attribute at index: ShapeModifierView_displayShapeName.
            mDisplayShapeName = attrsTypedArray.getBoolean(R.styleable.ShapeModifierView_displayShapeName, false);
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
        } finally {
            /*
             * Recycle the TypedArray, to be re-used by a later caller.
             * After calling this function you must not ever touch the typed array again.
             */
            attrsTypedArray.recycle();
        }
    }

    public int getShapeColor() {
        return this.mShapeColor;
    }

    public String getShapeName() {
        return this.mShapeName;
    }

    public boolean isDisplayShapeName() {
        return this.mDisplayShapeName;
    }

    public void setShapeColor(int shapeColor) {
        this.mShapeColor = shapeColor;
        /*
         * invalidate: Invalidate the whole view.
         * If the view is visible, onDraw(android.graphics.Canvas) will be called at some point in the future.
         * This must be called from a UI thread.
         * To call from a non-UI thread, call postInvalidate().
         */
        // To force a view to draw, call invalidate().
        invalidate();
        // To initiate a layout, call requestLayout().
        // This method is typically called by a view on itself when it believes that it can no longer fit within its current bounds.
        requestLayout();
    }

    public void setShapeName(String shapeName) {
        this.mShapeName = shapeName;
        // Force a view to draw.
        invalidate();
        // Initiate the layout.
        requestLayout();
    }

    public void setDisplayShapeName(boolean displayShapeName) {
        this.mDisplayShapeName = displayShapeName;
        // Force a view to draw.
        invalidate();
        // Initiate the layout.
        requestLayout();
    }
}
