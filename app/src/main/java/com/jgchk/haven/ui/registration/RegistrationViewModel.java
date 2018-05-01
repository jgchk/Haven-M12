package com.jgchk.haven.ui.registration;

import android.database.sqlite.SQLiteConstraintException;

import com.jgchk.haven.data.DataManager;
import com.jgchk.haven.data.model.db.User;
import com.jgchk.haven.ui.base.BaseViewModel;
import com.jgchk.haven.utils.rx.SchedulerProvider;

public class RegistrationViewModel extends BaseViewModel<RegistrationNavigator> {

    public RegistrationViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    @SuppressWarnings("FeatureEnvy")
    public void register(String email, String password, User.AccountType accountType) {
        setIsLoading(true);
        User user = new User(email, password, accountType);
        getCompositeDisposable().add(getDataManager()
                .insertUser(user)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    getDataManager().updateUserInfo(DataManager.LoggedInMode.LOGGED_IN_MODE_SERVER,
                            user);
                    setIsLoading(false);
                    getNavigator().openMainActivity();
                }, throwable -> {
                    setIsLoading(false);
                    if (throwable instanceof SQLiteConstraintException) {
                        getNavigator().onFailedRegistration();
                    } else {
                        getNavigator().handleError(throwable);
                    }
                })
        );
    }

    public void onRegistrationClick() {
        getNavigator().register();
    }

    public void onLoginClick() {
        getNavigator().openLoginActivity();
    }
}
