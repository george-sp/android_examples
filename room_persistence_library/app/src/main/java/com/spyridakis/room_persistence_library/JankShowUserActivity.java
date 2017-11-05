package com.spyridakis.room_persistence_library;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.spyridakis.room_persistence_library.database.AppDatabase;
import com.spyridakis.room_persistence_library.database.utils.DatabaseInitializer;
import com.spyridakis.room_persistence_library.entity.Book;

import java.util.List;

public class JankShowUserActivity extends AppCompatActivity {

    private AppDatabase mDb;

    private TextView mBooksTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.db_activity_refresh);

        mBooksTextView = findViewById(R.id.books_tv);

        // Note: Db references should not be in an activity.
        mDb = AppDatabase.getInMemoryDatabase(getApplicationContext());

        populateDb();

        fetchData();
    }

    @Override
    protected void onDestroy() {
        AppDatabase.destroyInstance();
        super.onDestroy();
    }

    private void populateDb() {
        DatabaseInitializer.populateAsync(mDb);
    }

    private void fetchData() {
        // This activity is executing a query on the main thread, making the UI perform badly.
        List<Book> books = mDb.bookDao().findBooksBorrowedByNameSync("Mike");
        showListInUI(books, mBooksTextView);
    }

    private static void showListInUI(final @NonNull List<Book> books,
                                     final TextView booksTextView) {
        StringBuilder sb = new StringBuilder();
        for (Book book : books) {
            sb.append(book.title);
            sb.append("\n");
        }
        booksTextView.setText(sb.toString());
    }

    public void onRefreshBtClicked(View view) {
        mBooksTextView.setText("");
        fetchData();
    }
}
