package com.spyridakis.carousel_layout_manager_example;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView mCountryNameTextView;
    private ImageView mDrawableImageView;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        mCountryNameTextView = (TextView) itemView.findViewById(R.id.country_name);
        mDrawableImageView = (ImageView) itemView.findViewById(R.id.country_photo);
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(v.getContext(), "Card position: " + getPosition(), Toast.LENGTH_SHORT);
    }

    public TextView getmCountryNameTextView() {
        return mCountryNameTextView;
    }

    public void setmCountryNameTextView(TextView mCountryNameTextView) {
        this.mCountryNameTextView = mCountryNameTextView;
    }

    public ImageView getmDrawableImageView() {
        return mDrawableImageView;
    }

    public void setmDrawableImageView(ImageView mDrawableImageView) {
        this.mDrawableImageView = mDrawableImageView;
    }
}
