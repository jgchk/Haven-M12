package com.jgchk.haven.ui.registration;

public interface RegistrationNavigator {

    void handleError(Throwable throwable);

    void register();

    void onFailedRegistration();

    void openMainActivity();

    void openLoginActivity();
}
