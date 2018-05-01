package com.jgchk.haven.data.local.prefs;

import com.jgchk.haven.data.DataManager;
import com.jgchk.haven.data.model.db.User;
import com.jgchk.haven.data.model.others.Restriction;

import java.util.Set;

public interface PreferencesHelper {

    User getCurrentUser();

    void setCurrentUser(User user);

    int getCurrentUserLoggedInMode();

    void setCurrentUserLoggedInMode(DataManager.LoggedInMode mode);

    int getVacanciesQuery();

    void setVacanciesQuery(int vacanciesQuery);

    Set<Restriction> getRestrictionsQuery();

    void addRestrictionQuery(Restriction restriction);

    void removeRestrictionQuery(Restriction restriction);
}
