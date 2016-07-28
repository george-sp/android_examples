package com.codeburrow.recycler_view_example;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author George Spiridakis <george@codeburrow.com>
 * @since Jul/27/2016.
 * ===================================================
 * ---------->    http://codeburrow.com    <----------
 * ===================================================
 */
/*
 * An ItemDecoration allows the application to add a special drawing and layout offset
 * to specific item views from the adapter's data set.
 * This can be useful for drawing dividers between items, highlights, visual grouping boundaries and more.
 */
public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    private static final String LOG_TAG = DividerItemDecoration.class.getSimpleName();

    private Drawable mDividerDrawable;

    public DividerItemDecoration(Drawable dividerDrawable) {
        this.mDividerDrawable = dividerDrawable;
    }

    /**
     * Retrieve any offsets for the given item.
     * Each field of outRect specifies the number of pixels that the item view should be inset by,
     * similar to padding or margin.
     * The default implementation sets the bounds of outRect to 0 and returns.
     * <p/>
     * If this ItemDecoration doesn't affect the positioning of item views,
     * it should set all four fields of outRect(left, top, right, bottom) to zero before returning.
     * <p/>
     * If you need to access Adapter for additional data,
     * you can call getChildAdapterPosition(View) to get the adapter position of the View.
     *
     * @param outRect Rect to retrieve the output.
     * @param view    The child view to decorate.
     * @param parent  RecyclerView this ItemDecoration is decorating.
     * @param state   The current state of RecyclerView.
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        if (parent.getChildAdapterPosition(view) == 0) {
            return;
        }
        outRect.top = mDividerDrawable.getIntrinsicHeight();
    }

    /**
     * Draw any appropriate decorations into the Canvas supplied to the RecyclerView.
     * Any content drawn by this method will be drawn before the item views are drawn,
     * and will thus appear underneath the views.
     *
     * @param canvas Canvas to draw into.
     * @param parent RecyclerView this ItemDecoration is drawing into.
     * @param state  The current state of RecyclerView.
     */
    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        int dividerLeft = parent.getPaddingLeft();
        int dividerRight = parent.getWidth() - parent.getPaddingRight();

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount - 1; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            int dividerTop = child.getBottom() + layoutParams.bottomMargin;
            int dividerBottom = dividerTop + mDividerDrawable.getIntrinsicHeight();

            mDividerDrawable.setBounds(dividerLeft, dividerTop, dividerRight, dividerBottom);
            mDividerDrawable.draw(canvas);
        }
    }
}
