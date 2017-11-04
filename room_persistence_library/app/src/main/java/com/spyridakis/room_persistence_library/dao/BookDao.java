package com.spyridakis.room_persistence_library.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Update;

import com.spyridakis.room_persistence_library.entity.Book;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;
import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface BookDao {

    @Insert(onConflict = IGNORE)
    void insertBook(Book book);

    @Update(onConflict = REPLACE)
    void updateBook(Book book);

    @Delete
    void deleteBook(Book book);
}
