package com.jgchk.haven.data;

import android.content.Context;
import android.location.Location;

import com.google.gson.Gson;
import com.jgchk.haven.data.local.db.DbHelper;
import com.jgchk.haven.data.local.location.LocationHelper;
import com.jgchk.haven.data.local.prefs.PreferencesHelper;
import com.jgchk.haven.data.model.db.Shelter;
import com.jgchk.haven.data.model.db.User;
import com.jgchk.haven.data.model.others.Restriction;
import com.jgchk.haven.utils.AppLogger;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;

public class AppDataManager implements DataManager {

    private final Context mContext;

    private final DbHelper mDbHelper;

    private final PreferencesHelper mPreferencesHelper;

    private final LocationHelper mLocationHelper;

    private final Gson mGson;

    @Inject
    public AppDataManager(Context context, DbHelper dbHelper, PreferencesHelper preferencesHelper,
                          LocationHelper locationHelper, Gson gson) {
        mContext = context;
        mDbHelper = dbHelper;
        mPreferencesHelper = preferencesHelper;
        mLocationHelper = locationHelper;
        mGson = gson;
    }

    @Override
    public Single<User> getUserByLogin(String email, String password) {
        return mDbHelper.getUserByLogin(email, password);
    }

    @Override
    public Observable<Boolean> insertUser(User user) {
        return mDbHelper.insertUser(user);
    }

    @Override
    public Observable<List<Shelter>> getAllShelters() {
        return mDbHelper.getAllShelters();
    }

    @Override
    public Observable<List<Shelter>> getSheltersMatching(String name, int vacancy, Set<Restriction> restrictions) {
        AppLogger.d("APPDATAMANAGER: " + mDbHelper.getAllShelters());
        return mDbHelper.getAllShelters();
    }

    @Override
    public void setUserAsLoggedOut() {
        updateUserInfo(LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT, null);
    }

    @Override
    public void updateUserInfo(LoggedInMode loggedInMode, String email) {
        setCurrentUserLoggedInMode(loggedInMode);
        setCurrentUserEmail(email);
    }

    @Override
    public String getCurrentUserEmail() {
        return mPreferencesHelper.getCurrentUserEmail();
    }

    @Override
    public void setCurrentUserEmail(String email) {
        mPreferencesHelper.setCurrentUserEmail(email);
    }

    @Override
    public Long getCurrentUserId() {
        return mPreferencesHelper.getCurrentUserId();
    }

    @Override
    public void setCurrentUserId(Long userId) {
        mPreferencesHelper.setCurrentUserId(userId);
    }

    @Override
    public int getCurrentUserLoggedInMode() {
        return mPreferencesHelper.getCurrentUserLoggedInMode();
    }

    @Override
    public void setCurrentUserLoggedInMode(LoggedInMode mode) {
        mPreferencesHelper.setCurrentUserLoggedInMode(mode);
    }

    @Override
    public Observable<Location> getLastLocation() {
        return mLocationHelper.getLastLocation();
    }
}
