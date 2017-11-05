package com.spyridakis.room_persistence_library;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.spyridakis.room_persistence_library.entity.Book;
import com.spyridakis.room_persistence_library.view_model.TypeConvertersViewModel;

import java.util.List;

public class TypeConvertersActivity extends AppCompatActivity {

    private TypeConvertersViewModel mViewModel;

    private TextView mBooksTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.db_activity_refresh);
        mBooksTextView = findViewById(R.id.books_tv);

        // Get a reference to the ViewModel for this screen.
        mViewModel = ViewModelProviders.of(this).get(TypeConvertersViewModel.class);

        // Update the UI whenever there's a change in the ViewModel's data.
        subscribeUiBooks();
    }

    public void onRefreshClicked(View view) {
        mViewModel.createDb();
    }

    private void subscribeUiBooks() {
        mViewModel.getBooks().observe(this, new Observer<List<Book>>() {
            @Override
            public void onChanged(@NonNull final List<Book> books) {
                showBooksInUi(books, mBooksTextView);
            }
        });
    }

    private static void showBooksInUi(final @NonNull List<Book> books,
                                      final TextView booksTextView) {
        StringBuilder sb = new StringBuilder();

        for (Book book : books) {
            sb.append(book.title);
            sb.append("\n");

        }
        booksTextView.setText(sb.toString());
    }
}
