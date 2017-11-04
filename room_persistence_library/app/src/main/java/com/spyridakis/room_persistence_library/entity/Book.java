package com.spyridakis.room_persistence_library.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Book {

    @PrimaryKey
    public String id;
    public String title;
}
