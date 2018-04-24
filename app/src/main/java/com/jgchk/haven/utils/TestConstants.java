package com.jgchk.haven.utils;

import android.location.Location;

import com.jgchk.haven.data.model.db.Shelter;
import com.jgchk.haven.data.model.db.User;
import com.jgchk.haven.data.model.others.Restriction;

import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("UtilityClass")
public class TestConstants {

    // User
    public static final String DUMMY_EMAIL = "test@email.com";
    public static final String DUMMY_PASSWORD = "password";
    public static final User.AccountType DUMMY_ACCOUNT_TYPE = User.AccountType.USER;
    public static final String ALT_EMAIL = "test2@email.com";
    public static final String ALT_PASSWORD = "password2";

    public static User getDummyUser() {
        return new User(DUMMY_EMAIL, DUMMY_PASSWORD, DUMMY_ACCOUNT_TYPE);
    }


    // Shelter
    private static final String NAME = "";
    private static final int CAPACITY = 0;
    private static final Location LOCATION = new Location("");
    private static final String ADDRESS = "";
    private static final String NOTES = "";
    private static final String PHONE = "";
    private static final Set<Restriction> RESTRICTIONS = new HashSet<>();

    public static Shelter getDummyShelter() {
        return new Shelter(NAME, CAPACITY, LOCATION, ADDRESS, NOTES, PHONE, RESTRICTIONS);
    }
}
