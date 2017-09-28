package com.spyridakis.carousel_layout_manager_example;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    private Context mContext;
    private List<CountryItem> mCountryItemsList;

    public RecyclerViewAdapter(Context context, List<CountryItem> countryItemsList) {
        this.mContext = context;
        this.mCountryItemsList = countryItemsList;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_item, parent, false);

        return new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        final CountryItem countryItem = mCountryItemsList.get(position);
        holder.getmCountryNameTextView().setText(countryItem.getName());
        holder.getmDrawableImageView().setImageResource(countryItem.getDrawableId());
    }

    @Override
    public int getItemCount() {
        return mCountryItemsList.size();
    }

}
