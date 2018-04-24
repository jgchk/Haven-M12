package com.jgchk.haven.data.local.db;

import android.content.Context;
import android.location.Location;

import com.jgchk.haven.R;
import com.jgchk.haven.data.model.db.Shelter;
import com.jgchk.haven.data.model.others.Restriction;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.siegmar.fastcsv.reader.CsvParser;
import de.siegmar.fastcsv.reader.CsvReader;
import de.siegmar.fastcsv.reader.CsvRow;

public class ShelterDatabasePopulator {

//    private static Shelter[] populateShelterDatabase(InputStream shelterDatabaseResource) {
//        BufferedReader reader = new BufferedReader(
//                new InputStreamReader(shelterDatabaseResource, Charset.forName("UTF-8")));
//        String line;
//        List<Shelter> shelters = new ArrayList<>();
//        try {
//            reader.readLine();
//            while ((line = reader.readLine()) != null) {
//                String[] tokens = line.split(",");
//                for (int i = 0; i < 8; i++) {
//                    AppLogger.d(i + ": " + tokens[i]);
//                }
//                String name = tokens[0];
//                int capacity = Integer.parseInt(tokens[1]);
//                Location location = new Location("");
//                location.setLatitude(Double.parseDouble(tokens[2]));
//                location.setLongitude(Double.parseDouble(tokens[3]));
//                String address = tokens[4];
//                String notes = tokens[5];
//                String phone = tokens[6];
//                Set<Restriction> restrictions = new HashSet<>();
//                for (String restriction : tokens[7].split(",")) {
//                    AppLogger.d(restriction);
//                    restrictions.add(Restriction.fromString(restriction));
//                }
//                shelters.add(
//                        new Shelter(name, capacity, location, address, notes, phone, restrictions));
//            }
//            return shelters.toArray(new Shelter[0]);
//        } catch (IOException e) {
//            // TODO: handle can't load shelters error
//            e.printStackTrace();
//        }
//        return null;
//    }

    public static Shelter[] populateShelterDatabase(Context context) {
        InputStreamReader isr = new InputStreamReader(
                context.getResources().openRawResource(R.raw.shelter_database),
                StandardCharsets.UTF_8);
        CsvReader reader = new CsvReader();
        try (CsvParser parser = reader.parse(isr)) {
            List<Shelter> shelters = new ArrayList<>();
            parser.nextRow(); // Skip column titles
            CsvRow row;
            while ((row = parser.nextRow()) != null) {
                String name = row.getField(0);
                int capacity = Integer.parseInt(row.getField(1));
                Location location = new Location("");
                location.setLatitude(Double.parseDouble(row.getField(2)));
                location.setLongitude(Double.parseDouble(row.getField(3)));
                String address = row.getField(4);
                String notes = row.getField(5);
                String phone = row.getField(6);
                Set<Restriction> restrictions = new HashSet<>();
                if (!row.getField(7).isEmpty()) {
                    for (String restriction : row.getField(7).split(",")) {
                        restrictions.add(Restriction.fromString(restriction));
                    }
                }
                shelters.add(
                        new Shelter(name, capacity, location, address, notes, phone, restrictions));
            }
            return shelters.toArray(new Shelter[0]);
        } catch (IOException e) {
            // TODO: handle can't load shelters error
            e.printStackTrace();
        }
        return null;
    }
}
