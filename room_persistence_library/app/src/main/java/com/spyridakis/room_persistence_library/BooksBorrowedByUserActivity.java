package com.spyridakis.room_persistence_library;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.spyridakis.room_persistence_library.entity.Book;
import com.spyridakis.room_persistence_library.view_model.BooksBorrowedByUserViewModel;

import java.util.List;

public class BooksBorrowedByUserActivity extends AppCompatActivity {

    private BooksBorrowedByUserViewModel mViewModel;

    @SuppressWarnings("unused")
    private TextView mBooksTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.db_activity_refresh);
        mBooksTextView = findViewById(R.id.books_tv);

        // Get a reference to the ViewModel for this screen.
        mViewModel = ViewModelProviders.of(this).get(BooksBorrowedByUserViewModel.class);

        // Update the UI whenever there's a change in the ViewModel's data.
        subscribeUiBooks();
    }

    public void onRefreshClicked(View view) {
        mViewModel.createDb();
    }

    private void subscribeUiBooks() {
        // Refresh the list of books when there's new data
        mViewModel.books.observe(this, new Observer<List<Book>>() {
            @Override
            public void onChanged(@NonNull final List<Book> books) {
                showBooksInUi(books, mBooksTextView);
            }
        });
    }

    private static void showBooksInUi(final @NonNull List<Book> books, final TextView booksTextView) {
        StringBuilder sb = new StringBuilder();

        for (Book book : books) {
            sb.append(book.title);
            sb.append("\n");

        }
        booksTextView.setText(sb.toString());
    }
}
