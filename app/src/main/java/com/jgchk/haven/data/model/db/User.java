package com.jgchk.haven.data.model.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Objects;

@Entity(tableName = "users")
public class User {

    @NonNull
    @PrimaryKey
    public String email;

    public String password;

    @ColumnInfo(name = "account_type")
    public AccountType accountType;

    public User(@NonNull String email, String password, AccountType accountType) {
        this.email = email;
        this.password = password;
        this.accountType = accountType;
    }

    public enum AccountType {
        USER("User"), ADMIN("Admin");

        private final String mString;

        AccountType(String string) {
            mString = string;
        }

        @Override
        public String toString() {
            return mString;
        }

        public static AccountType fromString(String str) {
            for (AccountType v : values()) {
                if (v.toString().equalsIgnoreCase(str)) {
                    return v;
                }
            }
            throw new IllegalArgumentException("No enum constant " + str);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if ((o == null) || (getClass() != o.getClass())) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", accountType=" + accountType +
                '}';
    }
}
