package com.jgchk.haven.data.local.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.jgchk.haven.data.model.db.Shelter;

import java.util.List;

@Dao
public interface ShelterDao {

    @Query("SELECT * FROM shelters")
    List<Shelter> loadAll();

    @Insert
    void insert(Shelter shelter);
}
