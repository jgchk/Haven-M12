package com.jgchk.haven.data.local.db.converter;

import android.arch.persistence.room.TypeConverter;
import android.location.Location;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

@SuppressWarnings("UtilityClass")
public class LocationTypeConverter {

    @TypeConverter
    public static Location toLocation(String value) {
        @SuppressWarnings("EmptyClass") Type t = new TypeToken<Location>() {}.getType();
        return new Gson().fromJson(value, t);
    }

    @TypeConverter
    public static String toString(Location location) {
        return new Gson().toJson(location);
    }
}
