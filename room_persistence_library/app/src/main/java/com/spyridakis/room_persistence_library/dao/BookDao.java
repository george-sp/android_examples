package com.spyridakis.room_persistence_library.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.spyridakis.room_persistence_library.entity.Book;

import java.util.Date;
import java.util.List;

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

    @Query("SELECT * FROM book WHERE id = :id")
    Book findBookById(int id);

    @Query("SELECT * FROM book")
    List<Book> findAllBooksSync();

    @Query("SELECT * FROM book")
    LiveData<List<Book>> findAllBooks();

    @Query("DELETE FROM book")
    void deleteAll();

    @Query("SELECT * FROM book " +
            "INNER JOIN loan ON loan.book_id = book.id " +
            "INNER JOIN user ON user.id = loan.user_id " +
            "WHERE user.name LIKE :userName"
    )
    List<Book> findBooksBorrowedByNameSync(String userName);

    @Query("SELECT * FROM book " +
            "INNER JOIN loan ON loan.book_id = book.id " +
            "INNER JOIN user ON User.id = loan.user_id " +
            "WHERE user.name LIKE :userName"
    )
    LiveData<List<Book>> findBooksBorrowedByName(String userName);

    @Query("SELECT * FROM book " +
            "INNER JOIN loan ON loan.book_id LIKE book.id " +
            "WHERE loan.user_id LIKE :userId "
    )
    List<Book> findBooksBorrowedByUserSync(String userId);

    @Query("SELECT * FROM book " +
            "INNER JOIN loan ON loan.book_id LIKE book.id " +
            "WHERE loan.user_id LIKE :userId"
    )
    LiveData<List<Book>> findBooksBorrowedByUser(String userId);

    @Query("SELECT * FROM book " +
            "INNER JOIN loan ON loan.book_id = book.id " +
            "INNER JOIN user ON user.id = loan.user_id " +
            "WHERE user.name LIKE :userName " +
            "AND loan.endTime > :after"
    )
    LiveData<List<Book>> findBooksBorrowedByNameAfter(String userName, Date after);

    @Query("SELECT * FROM book " +
            "INNER JOIN loan ON loan.book_id LIKE book.id " +
            "WHERE loan.user_id LIKE :userId " +
            "AND loan.endTime > :after "
    )
    LiveData<List<Book>> findBooksBorrowedByUserAfter(String userId, Date after);
}
