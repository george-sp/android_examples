package com.spyridakis.room_persistence_library.view_model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.spyridakis.room_persistence_library.database.AppDatabase;
import com.spyridakis.room_persistence_library.database.utils.DatabaseInitializer;
import com.spyridakis.room_persistence_library.entity.Book;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TypeConvertersViewModel extends AndroidViewModel {

    private LiveData<List<Book>> mBooks;

    private AppDatabase mDb;

    public TypeConvertersViewModel(Application application) {
        super(application);
        createDb();
    }

    public void createDb() {
        mDb = AppDatabase.getInMemoryDatabase(this.getApplication());

        // Populate it with initial data
        DatabaseInitializer.populateAsync(mDb);

        // Receive changes
        subscribeToDbChanges();
    }

    public LiveData<List<Book>> getBooks() {
        return mBooks;
    }

    private void subscribeToDbChanges() {
        // Books is a LiveData object so updates are observed.
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, -1);
        Date yesterday = calendar.getTime();
        mBooks = mDb.bookDao().findBooksBorrowedByNameAfter("Mike", yesterday);
    }
}
