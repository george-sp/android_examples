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

    public MoviesRecyclerViewAdapter(List<Movie> moviesList) {
        this.mMoviesList = moviesList;
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

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_recycler_view_item, parent, false);

        return new MovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder movieViewHolder, int position) {
        Movie movie = mMoviesList.get(position);
        movieViewHolder.mTitleTextView.setText(movie.getTitle());
        movieViewHolder.mYearTextView.setText(movie.getYear());
        movieViewHolder.mGenreTextView.setText(movie.getGenre());
    }

    @Override
    public int getItemCount() {
        return mMoviesList.size();
    }
}
