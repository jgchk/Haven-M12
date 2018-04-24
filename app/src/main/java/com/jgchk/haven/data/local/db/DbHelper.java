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
}
