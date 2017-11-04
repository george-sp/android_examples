package com.spyridakis.room_persistence_library.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.spyridakis.room_persistence_library.dto.LoanWithUserAndBook;
import com.spyridakis.room_persistence_library.entity.Loan;

import java.util.Date;
import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.ABORT;
import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface LoanDao {

    @Insert(onConflict = ABORT)
    void insertLoan(Loan loan);

    @Update(onConflict = REPLACE)
    void updateLoan(Loan loan);

    @Delete
    void deleteLoan(Loan loan);

    @Query("DELETE FROM loan")
    void deleteAll();

    @Query("SELECT * From loan")
    LiveData<List<Loan>> findAll();

    @Query("SELECT loan.id, book.title, user.name, loan.startTime, loan.endTime From loan " +
            "INNER JOIN book ON loan.book_id = book.id " +
            "INNER JOIN user ON loan.user_id = user.id")
    LiveData<List<LoanWithUserAndBook>> findAllWithUserAndBook();

    @Query("SELECT loan.id, book.title as title, user.name as name, loan.startTime, loan.endTime " +
            "FROM book " +
            "INNER JOIN loan ON loan.book_id = book.id " +
            "INNER JOIN user ON user.id = loan.user_id " +
            "WHERE user.name LIKE :userName " +
            "AND loan.endTime > :after"
    )
    LiveData<List<LoanWithUserAndBook>> findLoansByNameAfter(String userName, Date after);
}
