package com.jgchk.haven.data.local.db;

import com.jgchk.haven.data.model.db.Shelter;
import com.jgchk.haven.data.model.db.User;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

public interface DbHelper {

    Single<User> getUserByLogin(final String email, final String password);

    Observable<Boolean> insertUser(final User user);

    Observable<List<Shelter>> getAllShelters();

    Observable<Boolean> reserve(final Shelter shelter, final User user, final int numReservations);

    Observable<Boolean> release(final Shelter shelter, final User user);

    Observable<Integer> getVacancies(final Shelter shelter);

    Observable<Integer> getReservations(final Shelter shelter, final User user);
}
