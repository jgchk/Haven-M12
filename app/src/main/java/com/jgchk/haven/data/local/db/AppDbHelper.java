package com.jgchk.haven.data.local.db;

import com.jgchk.haven.data.model.db.Reservation;
import com.jgchk.haven.data.model.db.Shelter;
import com.jgchk.haven.data.model.db.User;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;

public class AppDbHelper implements DbHelper {

    private final AppDatabase mAppDatabase;

    @Inject
    public AppDbHelper(AppDatabase appDatabase) {
        mAppDatabase = appDatabase;
    }

    @Override
    public Single<User> getUserByLogin(final String email, final String password) {
        return Single.fromCallable(() -> mAppDatabase.userDao().findByLogin(email, password));
    }

    @Override
    public Observable<Boolean> insertUser(final User user) {
        return Observable.fromCallable(() -> {
            mAppDatabase.userDao().insert(user);
            return true;
        });
    }

    @Override
    public Observable<List<Shelter>> getAllShelters() {
        return Observable.fromCallable(() -> mAppDatabase.shelterDao().loadAll());
    }

    @Override
    public Observable<Boolean> reserve(final Shelter shelter, final User user, final int numReserved) {
        return Observable.fromCallable(() -> {
            mAppDatabase.reservationDao().insert(new Reservation(shelter.id, user.email, numReserved));
            return true;
        });
    }

    @Override
    public Observable<Boolean> release(final Shelter shelter, final User user) {
        return Observable.fromCallable(() -> {
            mAppDatabase.reservationDao().delete(shelter.id, user.email);
            return true;
        });
    }

    @Override
    public Observable<Integer> getVacancies(final Shelter shelter) {
        return Observable.fromCallable(() -> {
            if (shelter.capacity <= 0) {
                return 0;
            }

            List<Reservation> reservations = mAppDatabase.reservationDao().getAllAtShelter(shelter.id);
            int totalReservations = 0;
            for (Reservation reservation : reservations) {
                totalReservations += reservation.numReserved;
            }
            return shelter.capacity - totalReservations;
        });
    }

    @Override
    public Observable<Integer> getReservations(Shelter shelter, User user) {
        return Observable.fromCallable(() -> mAppDatabase.reservationDao().getUserReservation(shelter.id, user.email).numReserved);
    }
}
