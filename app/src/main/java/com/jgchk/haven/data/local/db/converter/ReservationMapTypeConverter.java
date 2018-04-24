package com.jgchk.haven.data.local.db.converter;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jgchk.haven.data.model.db.User;

import java.lang.reflect.Type;
import java.util.Map;

@SuppressWarnings("UtilityClass")
public class ReservationMapTypeConverter {

    @TypeConverter
    public static Map<User, Integer> toReservationMap(String value) {
        @SuppressWarnings("EmptyClass") Type t = new TypeToken<Map<User, Integer>>() {}.getType();
        return new Gson().fromJson(value, t);
    }

    @TypeConverter
    public static String toString(Map<User, Integer> reservationMap) {
        return new Gson().toJson(reservationMap);
    }
}
