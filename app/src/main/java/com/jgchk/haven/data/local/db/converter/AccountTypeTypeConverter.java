package com.jgchk.haven.data.local.db.converter;

import android.arch.persistence.room.TypeConverter;

import com.jgchk.haven.data.model.db.User;

@SuppressWarnings("UtilityClass")
public class AccountTypeTypeConverter {

    @TypeConverter
    public static User.AccountType toAccountType(String value) {
        if (value.equals(User.AccountType.USER.toString())) {
            return User.AccountType.USER;
        } else if (value.equals(User.AccountType.ADMIN.toString())) {
            return User.AccountType.ADMIN;
        } else {
            throw new IllegalArgumentException("Invalid account type");
        }
    }

    @TypeConverter
    public static String toString(User.AccountType accountType) {
        return accountType.toString();
    }
}
