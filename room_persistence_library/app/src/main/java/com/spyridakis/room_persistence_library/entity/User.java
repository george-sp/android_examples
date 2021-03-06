package com.spyridakis.room_persistence_library.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(indices = {@Index(value = {"name", "lastName"}, unique = true)})
public class User {

    @PrimaryKey
    @NonNull
    public String id;
    public String name;
    public String lastName;
    public int age;
}
