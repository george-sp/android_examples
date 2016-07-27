package com.codeburrow.recycler_view_example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    private List<Movie> mMoviesList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private MoviesRecyclerViewAdapter mMoviesRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        fillMoviesList();
        mMoviesRecyclerViewAdapter = new MoviesRecyclerViewAdapter(mMoviesList);
        mRecyclerView.setAdapter(mMoviesRecyclerViewAdapter);
    }

    private void fillMoviesList() {
        mMoviesList.add(new Movie("Mad Max: Fury Road", "Action & Adventure", "2015"));
        mMoviesList.add(new Movie("Inside Out", "Animation, Kids & Family", "2015"));
        mMoviesList.add(new Movie("Star Wars: Episode VII - The Force Awakens", "Action", "2015"));
        mMoviesList.add(new Movie("Shaun the Sheep", "Animation", "2015"));
        mMoviesList.add(new Movie("The Martian", "Science Fiction & Fantasy", "2015"));
        mMoviesList.add(new Movie("Mission Impossible: Rogue Nation", "Action", "2015"));
        mMoviesList.add(new Movie("Up", "Animation", "2009"));
        mMoviesList.add(new Movie("Star Trek", "Science Fiction", "2009"));
        mMoviesList.add(new Movie("The LEGO Movie", "Animation", "2014"));
        mMoviesList.add(new Movie("Iron Man", "Action & Adventure", "2008"));
        mMoviesList.add(new Movie("Aliens", "Science Fiction", "1686"));
        mMoviesList.add(new Movie("Chicken Run", "Animation", "2000"));
        mMoviesList.add(new Movie("Back to the Future", "Science Fiction", "1985"));
        mMoviesList.add(new Movie("Raiders of the Lost Ark", "Action & Adventure", "1981"));
        mMoviesList.add(new Movie("Goldfinger", "Action & Adventure", "1965"));
        mMoviesList.add(new Movie("Guardians of the Galaxy", "Science Fiction & Fantasy", "2014"));
    }
}
