package com.spyridakis.room_persistence_library.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.spyridakis.room_persistence_library.converter.DateConverter;
import com.spyridakis.room_persistence_library.dao.BookDao;
import com.spyridakis.room_persistence_library.dao.LoanDao;
import com.spyridakis.room_persistence_library.dao.UserDao;
import com.spyridakis.room_persistence_library.entity.Book;
import com.spyridakis.room_persistence_library.entity.Loan;
import com.spyridakis.room_persistence_library.entity.User;

@Database(entities = {User.class, Book.class, Loan.class}, version = 1)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase DATABASE_INSTANCE;

    public abstract UserDao userDao();

    public abstract BookDao bookDao();

    public abstract LoanDao loanDao();

    public static AppDatabase getInMemoryDatabase(Context context) {
        if (DATABASE_INSTANCE == null) {
            DATABASE_INSTANCE =
                    Room.inMemoryDatabaseBuilder(context.getApplicationContext(), AppDatabase.class)
                            // Don't do this on a real app! (DO NOT allow queries on the main thread)
                            .allowMainThreadQueries()
                            .build();
        }
        return DATABASE_INSTANCE;
    }

    public static void destroyInstance() {
        DATABASE_INSTANCE = null;
    }

}
