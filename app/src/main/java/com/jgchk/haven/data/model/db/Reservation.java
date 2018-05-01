package com.jgchk.haven.data.model.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "reservations", foreignKeys = @ForeignKey(entity = Shelter.class,
        parentColumns = "id",
        childColumns = "shelter_id",
        onDelete = CASCADE))
public class Reservation {

    @ColumnInfo(name = "shelter_id")
    public Long shelterId;

    @PrimaryKey
    @ColumnInfo(name = "user_email")
    @NonNull
    public String userEmail;

    @ColumnInfo(name = "num_reserved")
    public int numReserved;

    public Reservation(Long shelterId, @NonNull String userEmail, int numReserved) {
        this.shelterId = shelterId;
        this.userEmail = userEmail;
        this.numReserved = numReserved;
    }
}
