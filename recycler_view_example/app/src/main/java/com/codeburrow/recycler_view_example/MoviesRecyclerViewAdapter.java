package com.codeburrow.recycler_view_example;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * @author George Spiridakis <george@codeburrow.com>
 * @since Jul/26/2016.
 * ===================================================
 * ---------->    http://codeburrow.com    <----------
 * ===================================================
 */
public class MoviesRecyclerViewAdapter extends RecyclerView.Adapter<MoviesRecyclerViewAdapter.MovieViewHolder> {

    private static final String LOG_TAG = MoviesRecyclerViewAdapter.class.getSimpleName();
    private List<Movie> mMoviesList;
    private OnRecyclerViewItemClickListener mClickListener;

    public MoviesRecyclerViewAdapter(List<Movie> moviesList, OnRecyclerViewItemClickListener clickListener) {
        this.mMoviesList = moviesList;
        this.mClickListener = clickListener;
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        public TextView mTitleTextView;
        public TextView mYearTextView;
        public TextView mGenreTextView;

        public MovieViewHolder(View itemView) {
            super(itemView);
            mTitleTextView = (TextView) itemView.findViewById(R.id.title_text_view);
            mYearTextView = (TextView) itemView.findViewById(R.id.year_text_view);
            mGenreTextView = (TextView) itemView.findViewById(R.id.genre_text_view);
        }
    }

    /**
     * Called when RecyclerView needs a new RecyclerView.ViewHolder of the given type to represent an item.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_recycler_view_item, parent, false);

        return new MovieViewHolder(itemView);
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     * This method should update the contents of the itemView to reflect the item at the given position.
     *
     * @param movieViewHolder The ViewHolder which should be updated to represent the contents of the item at the given position in the data set.
     * @param position        The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(MovieViewHolder movieViewHolder, int position) {
        final Movie movie = mMoviesList.get(position);
        movieViewHolder.mTitleTextView.setText(movie.getTitle());
        movieViewHolder.mYearTextView.setText(movie.getYear());
        movieViewHolder.mGenreTextView.setText(movie.getGenre());
        movieViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onRecyclerViewItemClick(movie);
            }
        });
    }

    /**
     * @return The total number of items in the data set hold by the adapter.
     */
    @Override
    public int getItemCount() {
        return mMoviesList.size();
    }

    public interface OnRecyclerViewItemClickListener {

        void onRecyclerViewItemClick(Movie movie);
    }
}
