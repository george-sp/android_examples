package com.spyridakis.room_persistence_library.dto;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.TypeConverters;

import com.spyridakis.room_persistence_library.converter.DateConverter;

import java.util.Date;

public class LoanWithUserAndBook {

    public String id;
    @ColumnInfo(name = "title")
    public String bookTitle;
    @ColumnInfo(name = "name")
    public String userName;
    @TypeConverters(DateConverter.class)
    public Date startTime;
    @TypeConverters(DateConverter.class)
    public Date endTime;
}
