package com.jgchk.haven.data.local.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.jgchk.haven.data.model.db.Reservation;

import java.util.List;

@Dao
public interface ReservationDao {

    @Insert
    void insert(Reservation reservation);

    @Query("DELETE FROM reservations WHERE shelter_id = :shelterId AND user_email = :userEmail")
    void delete(long shelterId, String userEmail);

    @Query("SELECT * FROM reservations WHERE shelter_id = :shelterId")
    List<Reservation> getAllAtShelter(long shelterId);

    @Query("SELECT * FROM reservations WHERE shelter_id = :shelterId AND user_email = :userEmail")
    Reservation getUserReservation(long shelterId, String userEmail);

}
