package com.jgchk.haven.ui.login;

import com.jgchk.haven.data.DataManager;
import com.jgchk.haven.ui.base.BaseViewModel;
import com.jgchk.haven.utils.rx.SchedulerProvider;

public class LoginViewModel extends BaseViewModel<LoginNavigator> {

    public LoginViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    @SuppressWarnings("FeatureEnvy")
    public void login(String email, String password) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getUserByLogin(email, password)
                .doOnSuccess(response -> getDataManager().updateUserInfo(
                        DataManager.LoggedInMode.LOGGED_IN_MODE_SERVER,
                        response.email))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(user -> {
                    setIsLoading(false);
                    getNavigator().openMainActivity();
                }, throwable -> {
                    setIsLoading(false);
                    if (throwable instanceof NullPointerException) {
                        getNavigator().onFailedLogin();
                    } else {
                        getNavigator().handleError(throwable);
                    }
                })
        );
    }

    public void onLoginClick() {
        getNavigator().login();
    }

    public void onRegistrationClick() {
        getNavigator().openRegistrationActivity();
    }
}
