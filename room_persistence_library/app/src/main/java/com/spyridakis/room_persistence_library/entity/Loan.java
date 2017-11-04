package com.spyridakis.room_persistence_library.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

//@formatter:off
@Entity(foreignKeys = {
            @ForeignKey(entity = Book.class, parentColumns = "id", childColumns = "book_id"),
            @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "user_id")
        }
)
//@formatter:on
public class Loan {

    @PrimaryKey
    public String id;
    public Date startTime;
    public Date endTime;
    @ColumnInfo(name = "book_id")
    public String bookId;
    @ColumnInfo(name = "user_id")
    public String userId;
}
