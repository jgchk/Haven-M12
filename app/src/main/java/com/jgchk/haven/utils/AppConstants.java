package com.jgchk.haven.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.jgchk.haven.data.model.db.User;
import com.jgchk.haven.data.model.others.Restriction;
import com.jgchk.haven.utils.gsonadapters.ReservationsMapAdapter;
import com.jgchk.haven.utils.gsonadapters.UserAdapter;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("UtilityClass")
public final class AppConstants {

    public static final long NULL_INDEX = -1L;

    public static final String DB_NAME = "haven.db";

    public static final String PREF_NAME = "haven_pref";

    public static final User.AccountType[] ACCOUNT_TYPES = User.AccountType.values();

    public static final String FILTER_NAME_DEFAULT = "";

    public static final int FILTER_VACANCIES_DEFAULT = 0;

    public static final Set<Restriction> FILTER_RESTRICTIONS_DEFAULT = new HashSet<>();

    public static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(User.class, new UserAdapter())
            .registerTypeAdapter(new TypeToken<Map<User, Integer>>() {}.getType(), new ReservationsMapAdapter())
            .setPrettyPrinting()
            .create();

    private AppConstants() {
        // This utility class is not publicly instantiable
    }
}
