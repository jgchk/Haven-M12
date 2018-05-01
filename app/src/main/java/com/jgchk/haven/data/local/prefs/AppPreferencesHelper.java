package com.jgchk.haven.data.local.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jgchk.haven.data.DataManager;
import com.jgchk.haven.data.model.db.User;
import com.jgchk.haven.data.model.others.Restriction;
import com.jgchk.haven.di.PreferenceInfo;
import com.jgchk.haven.utils.AppConstants;
import com.jgchk.haven.utils.AppLogger;

import java.util.Set;

import javax.inject.Inject;

public class AppPreferencesHelper implements PreferencesHelper {

    private static final String PREF_KEY_CURRENT_USER = "PREF_KEY_CURRENT_USER";

    private static final String PREF_KEY_USER_LOGGED_IN_MODE = "PREF_KEY_USER_LOGGED_IN_MODE";

    private static final String PREF_KEY_VACANCIES_QUERY = "PREF_KEY_VACANCIES_QUERY";

    private static final String PREF_KEY_RESTRICTIONS_QUERY = "PREF_KEY_RESTRICTIONS_QUERY";

    private final SharedPreferences mPrefs;

    private final Gson mGson;

    @Inject
    public AppPreferencesHelper(Context context, @PreferenceInfo String prefFileName, Gson gson) {
        mPrefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
        mGson = gson;
    }

    @Override
    public User getCurrentUser() {
        String userString = mPrefs.getString(PREF_KEY_CURRENT_USER, null);
        AppLogger.d(String.valueOf(userString));
        if (userString == null) {
            return null;
        }
        User user = mGson.fromJson(userString, User.class);
        AppLogger.d("GET CURRENT USER: " + String.valueOf(user));
        return user;
    }

    @Override
    public void setCurrentUser(User user) {
        AppLogger.d("SETTING CURRENT USER: " + String.valueOf(user));
        AppLogger.d(mGson.toJson(user));
        mPrefs.edit().putString(PREF_KEY_CURRENT_USER, mGson.toJson(user)).apply();
    }

    @Override
    public int getCurrentUserLoggedInMode() {
        return mPrefs.getInt(PREF_KEY_USER_LOGGED_IN_MODE,
                DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT.getType());
    }

    @Override
    public void setCurrentUserLoggedInMode(DataManager.LoggedInMode mode) {
        mPrefs.edit().putInt(PREF_KEY_USER_LOGGED_IN_MODE, mode.getType()).apply();
    }

    @Override
    public int getVacanciesQuery() {
        return mPrefs.getInt(PREF_KEY_VACANCIES_QUERY, AppConstants.FILTER_VACANCIES_DEFAULT);
    }

    @Override
    public void setVacanciesQuery(int vacanciesQuery) {
        mPrefs.edit().putInt(PREF_KEY_VACANCIES_QUERY, vacanciesQuery).apply();
    }

    @Override
    public Set<Restriction> getRestrictionsQuery() {
        String restrictionsQueryString = mPrefs.getString(PREF_KEY_RESTRICTIONS_QUERY, null);
        if (restrictionsQueryString == null) {
            return AppConstants.FILTER_RESTRICTIONS_DEFAULT;
        }
        return mGson.fromJson(restrictionsQueryString, new TypeToken<Set<Restriction>>() {
        }.getType());
    }

    @Override
    public void addRestrictionQuery(Restriction restriction) {
        Set<Restriction> restrictionsQuery = getRestrictionsQuery();
        restrictionsQuery.add(restriction);
        setRestrictionsQuery(restrictionsQuery);
    }

    @Override
    public void removeRestrictionQuery(Restriction restriction) {
        Set<Restriction> restrictionsQuery = getRestrictionsQuery();
        restrictionsQuery.remove(restriction);
        setRestrictionsQuery(restrictionsQuery);
    }

    private void setRestrictionsQuery(Set<Restriction> restrictionsQuery) {
        mPrefs.edit().putString(PREF_KEY_RESTRICTIONS_QUERY, mGson.toJson(restrictionsQuery)).apply();
    }
}
