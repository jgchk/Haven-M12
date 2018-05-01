package com.jgchk.haven.utils.gsonadapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.jgchk.haven.data.model.db.User;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.jgchk.haven.utils.AppConstants.GSON;

public class ReservationsMapAdapter extends TypeAdapter<Map<User, Integer>> {
    @Override
    public void write(JsonWriter out, Map<User, Integer> value) throws IOException {
        out.beginArray();
        for (Map.Entry<User, Integer> e : value.entrySet()) {
            out.name(GSON.toJson(e.getKey())).value(e.getValue());
        }
        out.endArray();
    }

    @Override
    public Map<User, Integer> read(JsonReader in) throws IOException {
        Map<User, Integer> out = new HashMap<>();

        in.beginObject();
        in.beginArray();
        while (in.hasNext()) {
            out.put(GSON.fromJson(in.nextName(), User.class), in.nextInt());
        }
        in.endArray();
        in.endObject();

        return out;
    }
}
