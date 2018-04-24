package com.jgchk.haven.ui.login;

public interface LoginNavigator {

    void handleError(Throwable throwable);

    void login();

    void onFailedLogin();

    void openMainActivity();

    void openRegistrationActivity();
}
