package com.jgchk.haven.utils.gsonadapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.jgchk.haven.data.model.db.User;
import com.jgchk.haven.utils.AppLogger;

import java.io.IOException;

public class UserAdapter extends TypeAdapter<User> {

    private static final String USER_EMAIL = "email";

    private static final String USER_PASSWORD = "password";

    private static final String USER_ACCOUNT_TYPE = "account_type";

    @Override
    public void write(JsonWriter out, User value) throws IOException {
        AppLogger.d("WRITE");

        out.beginObject();
        out.name(USER_EMAIL).value(value.email);
        out.name(USER_PASSWORD).value(value.password);
        out.name(USER_ACCOUNT_TYPE).value(value.accountType.toString());
        out.endObject();

        AppLogger.d(out.toString());
    }

    @Override
    public User read(JsonReader in) throws IOException {
        AppLogger.d("READ");

        String email = null, password = null, accountType = null;

        in.beginObject();
        while (in.hasNext()) {
            switch (in.nextName()) {
                case USER_EMAIL:
                    email = in.nextString();
                    break;
                case USER_PASSWORD:
                    password = in.nextString();
                    break;
                case USER_ACCOUNT_TYPE:
                    accountType = in.nextString();
                    break;
            }
        }
        in.endObject();

        return new User(email, password, User.AccountType.fromString(accountType));
    }
}
