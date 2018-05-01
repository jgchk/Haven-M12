package com.jgchk.haven.data.local.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.ContentValues;
import android.content.Context;
import android.support.annotation.NonNull;

import com.jgchk.haven.data.local.db.dao.ReservationDao;
import com.jgchk.haven.data.model.db.Reservation;
import com.jgchk.haven.utils.typeconverters.AccountTypeTypeConverter;
import com.jgchk.haven.utils.typeconverters.LocationTypeConverter;
import com.jgchk.haven.utils.typeconverters.ReservationMapTypeConverter;
import com.jgchk.haven.utils.typeconverters.RestrictionSetTypeConverter;
import com.jgchk.haven.data.local.db.dao.ShelterDao;
import com.jgchk.haven.data.local.db.dao.UserDao;
import com.jgchk.haven.data.model.db.Shelter;
import com.jgchk.haven.data.model.db.User;

@Database(entities = {User.class, Shelter.class, Reservation.class}, version = 8)
@TypeConverters({LocationTypeConverter.class, RestrictionSetTypeConverter.class,
        AccountTypeTypeConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;
    private static Shelter[] SHELTERS;

    private static Callback CALLBACK = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            for (int i = 0; i < SHELTERS.length; i++) {
                Shelter shelter = SHELTERS[i];
                ContentValues cv = new ContentValues();
                cv.put("id", i);
                cv.put("name", shelter.name);
                cv.put("capacity", shelter.capacity);
                cv.put("location", LocationTypeConverter.toString(shelter.location));
                cv.put("address", shelter.address);
                cv.put("notes", shelter.notes);
                cv.put("phone", shelter.phone);
                cv.put("restrictions", RestrictionSetTypeConverter.toString(shelter.restrictions));
                db.insert("shelters", OnConflictStrategy.IGNORE, cv);
            }
        }
    };

    public synchronized static AppDatabase getInstance(Context context, String dbName) {
        if (INSTANCE == null) {
            SHELTERS = ShelterDatabasePopulator.populateShelterDatabase(context);
            INSTANCE = Room.databaseBuilder(context, AppDatabase.class, dbName)
                    .fallbackToDestructiveMigration()
                    .addCallback(CALLBACK)
                    .build();
        }
        return INSTANCE;
    }

    public synchronized static AppDatabase getTestInstance(Context context) {
        SHELTERS = ShelterDatabasePopulator.populateShelterDatabase(context);
        return Room.inMemoryDatabaseBuilder(context, AppDatabase.class)
                .fallbackToDestructiveMigration()
                .addCallback(CALLBACK)
                .build();
    }

    public abstract ShelterDao shelterDao();

    public abstract UserDao userDao();

    public abstract ReservationDao reservationDao();
}
