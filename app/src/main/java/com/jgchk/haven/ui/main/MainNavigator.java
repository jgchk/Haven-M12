package com.jgchk.haven.ui.main;

public interface MainNavigator {

    void handleError(Throwable throwable);

    void openLoginActivity();

    void openFilterDialog();
}
