package com.jgchk.haven.data.local.db;

import com.jgchk.haven.data.model.db.Shelter;
import com.jgchk.haven.data.model.db.User;

import java.util.List;

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
}
