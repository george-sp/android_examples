package com.spyridakis.room_persistence_library.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.spyridakis.room_persistence_library.entity.Loan;

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
}
