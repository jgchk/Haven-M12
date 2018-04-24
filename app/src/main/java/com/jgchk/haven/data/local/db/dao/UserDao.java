package com.jgchk.haven.data.local.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.jgchk.haven.data.model.db.User;

@Dao
public interface UserDao {

    @Delete
    void delete(User user);

    @Query("SELECT * FROM users WHERE email = :email")
    User findByEmail(String email);

    @Query("SELECT * FROM users WHERE email = :email AND password = :password")
    User findByLogin(String email, String password);

    @Insert(onConflict = OnConflictStrategy.FAIL)
    void insert(User user);
}
