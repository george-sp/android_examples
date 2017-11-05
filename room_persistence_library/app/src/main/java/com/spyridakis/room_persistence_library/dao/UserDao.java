package com.spyridakis.room_persistence_library.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.spyridakis.room_persistence_library.entity.User;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface UserDao {

    @Insert(onConflict = IGNORE)
    void insertUser(User user);

    @Insert(onConflict = IGNORE)
    void insertOrReplaceUsers(User... users);

    @Update
    public void updateUsers(User... users);

    @Delete
    void deleteUser(User user);

    @Delete
    void deleteBothUsers(User user1, User user2);

    @Delete
    void deleteUsers(User... users);

    @Query("SELECT * FROM user")
    List<User> loadAllUsers();

    @Query("SELECT * FROM user WHERE id = :id")
    User loadUserById(int id);

    @Query("SELECT * FROM user WHERE name = :firstName AND lastName = :lastName")
    List<User> findByNameAndLastName(String firstName, String lastName);

    @Query("DELETE FROM user WHERE name LIKE :badName OR lastName LIKE :badName")
    int deleteUsersByName(String badName);

    @Query("SELECT * FROM user WHERE age < :age")
    List<User> findYoungerThan(int age);

    @Query("DELETE FROM user")
    void deleteAll();
}
