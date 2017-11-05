package com.spyridakis.room_persistence_library.view_model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.spyridakis.room_persistence_library.database.AppDatabase;
import com.spyridakis.room_persistence_library.database.utils.DatabaseInitializer;
import com.spyridakis.room_persistence_library.entity.Book;

import java.util.List;

public class BooksBorrowedByUserViewModel extends AndroidViewModel {

    public final LiveData<List<Book>> books;

    private AppDatabase mDb;

    public BooksBorrowedByUserViewModel(Application application) {
        super(application);
        createDb();

        // Books is a LiveData object so updates are observed.
        books = mDb.bookDao().findBooksBorrowedByName("Mike");
    }

    public void createDb() {
        mDb = AppDatabase.getInMemoryDatabase(this.getApplication());

        // Populate it with initial data
        DatabaseInitializer.populateAsync(mDb);
    }
}
