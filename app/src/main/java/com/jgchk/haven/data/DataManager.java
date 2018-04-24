package com.jgchk.haven.data;

import com.jgchk.haven.data.local.db.DbHelper;
import com.jgchk.haven.data.local.location.LocationHelper;
import com.jgchk.haven.data.local.prefs.PreferencesHelper;
import com.jgchk.haven.data.model.db.Shelter;
import com.jgchk.haven.data.model.others.Restriction;

import java.util.List;
import java.util.Set;

import io.reactivex.Observable;

public interface DataManager extends DbHelper, PreferencesHelper, LocationHelper {

    Observable<List<Shelter>> getSheltersMatching(String name, int vacancy, Set<Restriction> restrictions);

    void setUserAsLoggedOut();

    void updateUserInfo(LoggedInMode loggedInMode, String email);

    enum LoggedInMode {

        LOGGED_IN_MODE_LOGGED_OUT(0),
        LOGGED_IN_MODE_SERVER(1);

        private final int mType;

        LoggedInMode(int type) {
            mType = type;
        }

        public int getType() {
            return mType;
        }
    }
}
