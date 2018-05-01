package com.jgchk.haven.utils.typeconverters;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jgchk.haven.data.model.db.User;
import com.jgchk.haven.utils.AppLogger;

import java.lang.reflect.Type;
import java.util.Map;

import static com.jgchk.haven.utils.AppConstants.GSON;

@SuppressWarnings("UtilityClass")
public class ReservationMapTypeConverter {

//    @TypeConverter
//    public static Map<User, Integer> toReservationMap(String value) {
//        AppLogger.d(String.valueOf(value));
//        Type mapType = new TypeToken<Map<User, Integer>>() {}.getType();
//        return GSON.fromJson(value, mapType);
//    }
//
//    @TypeConverter
//    public static String toString(Map<User, Integer> reservationMap) {
//        return GSON.toJson(reservationMap);
//    }
}
