package com.jgchk.haven.data.local.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.jgchk.haven.data.model.db.Reservation;
import com.jgchk.haven.data.model.db.Shelter;
import com.jgchk.haven.data.model.db.User;

import java.util.List;

@Dao
public interface ShelterDao {

    @Query("SELECT * FROM shelters")
    List<Shelter> loadAll();

    @Insert
    void insert(Shelter shelter);

}
