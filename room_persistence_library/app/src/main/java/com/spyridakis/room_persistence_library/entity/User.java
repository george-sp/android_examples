package com.spyridakis.room_persistence_library.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class User {

    @PrimaryKey
    @NonNull
    public String id;
    public String name;
    public String lastName;
    public int age;
}
