package com.spyridakis.room_persistence_library.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.spyridakis.room_persistence_library.converter.DateConverter;

import java.util.Date;

//@formatter:off
@Entity(foreignKeys = {
            @ForeignKey(entity = Book.class, parentColumns = "id", childColumns = "book_id"),
            @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "user_id")
        },
        indices = {
            @Index(value = {"book_id"}, unique = true),
            @Index(value = {"user_id"}, unique = true)
        }
)
@TypeConverters(DateConverter.class)
//@formatter:on
public class Loan {

    @PrimaryKey
    @NonNull
    public String id;
    public Date startTime;
    public Date endTime;
    @ColumnInfo(name = "book_id")
    public String bookId;
    @ColumnInfo(name = "user_id")
    public String userId;
}
