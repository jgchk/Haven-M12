package com.jgchk.haven.utils.typeconverters;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jgchk.haven.data.model.others.Restriction;

import java.lang.reflect.Type;
import java.util.Set;

@SuppressWarnings("UtilityClass")
public class RestrictionSetTypeConverter {

    @TypeConverter
    public static Set<Restriction> toSet(String value) {
        @SuppressWarnings("EmptyClass") Type t = new TypeToken<Set<Restriction>>() {}.getType();
        return new Gson().fromJson(value, t);
    }

    @TypeConverter
    public static String toString(Set<Restriction> restrictionSet) {
        return new Gson().toJson(restrictionSet);
    }
}
