package com.jgchk.haven.data.local.prefs;

import com.jgchk.haven.data.DataManager;

public interface PreferencesHelper {

    String getCurrentUserEmail();

    void setCurrentUserEmail(String email);

    Long getCurrentUserId();

    void setCurrentUserId(Long userId);

    int getCurrentUserLoggedInMode();

    void setCurrentUserLoggedInMode(DataManager.LoggedInMode mode);
}
