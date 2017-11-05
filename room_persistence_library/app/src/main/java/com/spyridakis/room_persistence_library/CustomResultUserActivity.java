package com.spyridakis.room_persistence_library;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.spyridakis.room_persistence_library.view_model.CustomResultViewModel;

public class CustomResultUserActivity extends AppCompatActivity {

    private CustomResultViewModel mShowUserViewModel;

    private TextView mBooksTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.db_activity_refresh);
        mBooksTextView = findViewById(R.id.books_tv);

        mShowUserViewModel = ViewModelProviders.of(this).get(CustomResultViewModel.class);

        populateDb();

        subscribeUiLoans();
    }

    private void populateDb() {
        mShowUserViewModel.createDb();
    }

    private void subscribeUiLoans() {
        mShowUserViewModel.getLoansResult().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable final String result) {
                mBooksTextView.setText(result);
            }
        });
    }

    public void onRefreshBtClicked(View view) {
        populateDb();
        subscribeUiLoans();
    }
}
