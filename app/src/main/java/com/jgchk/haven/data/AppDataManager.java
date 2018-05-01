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
import io.reactivex.functions.Predicate;

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
    public Observable<Boolean> reserve(Shelter shelter, User user, int numReservations) {
        return mDbHelper.reserve(shelter, user, numReservations);
    }

    @Override
    public Observable<Boolean> release(Shelter shelter, User user) {
        return mDbHelper.release(shelter, user);
    }

    @Override
    public Observable<Integer> getVacancies(Shelter shelter) {
        return mDbHelper.getVacancies(shelter);
    }

    @Override
    public Observable<Integer> getReservations(Shelter shelter, User user) {
        return mDbHelper.getReservations(shelter, user);
    }

//    @Override
//    public Single<List<Shelter>> getSheltersMatching(String name, int vacancy, Set<Restriction> restrictions) {
//        AppLogger.d("APPDATAMANAGER: " + mDbHelper.getAllShelters());
//        return mDbHelper.getAllShelters()
//                .flatMapIterable(shelters -> shelters).filter(
//                        new Predicate<Shelter>() {
//                            @Override
//                            public boolean test(Shelter shelter) throws Exception {
//                                getVacancies(shelter)
//                            }
//                        }
////                        shelter -> {
////                            shelter.name.toLowerCase().contains(name.toLowerCase())
////                                    && (shelter.getVacancies() == -1 || shelter.getVacancies() >= vacancy)
////                                    && shelter.restrictions.containsAll(restrictions);
////                        })
//                .toList();
//    }

    @Override
    public void setUserAsLoggedOut() {
        updateUserInfo(LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT, null);
    }

    @Override
    public void updateUserInfo(LoggedInMode loggedInMode, User user) {
        AppLogger.d("UPDATE: " + String.valueOf(user));
        setCurrentUserLoggedInMode(loggedInMode);
        setCurrentUser(user);
    }

    @Override
    public User getCurrentUser() {
        return mPreferencesHelper.getCurrentUser();
    }

    @Override
    public void setCurrentUser(User user) {
        mPreferencesHelper.setCurrentUser(user);
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
    public int getVacanciesQuery() {
        return mPreferencesHelper.getVacanciesQuery();
    }

    @Override
    public void setVacanciesQuery(int vacanciesQuery) {
        mPreferencesHelper.setVacanciesQuery(vacanciesQuery);
    }

    @Override
    public Set<Restriction> getRestrictionsQuery() {
        return mPreferencesHelper.getRestrictionsQuery();
    }

    @Override
    public void addRestrictionQuery(Restriction restriction) {
        mPreferencesHelper.addRestrictionQuery(restriction);
    }

    @Override
    public void removeRestrictionQuery(Restriction restriction) {
        mPreferencesHelper.removeRestrictionQuery(restriction);
    }

    @Override
    public Single<Location> getLastLocation() {
        return mLocationHelper.getLastLocation();
    }
}
