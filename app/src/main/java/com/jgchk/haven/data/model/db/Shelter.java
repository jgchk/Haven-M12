package com.jgchk.haven.data.model.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.location.Location;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jgchk.haven.data.model.others.Restriction;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity(tableName = "shelters")
public class Shelter implements Serializable {

    private static final double METERS_TO_MILES_CONVERSION_FACTOR = 0.000621371192;

    @PrimaryKey
    public Long id;

    public String name;

    public int capacity;

    public Location location;

    public String address;

    public String notes;

    public String phone;

    public Set<Restriction> restrictions;

    @Ignore
    public double distance;

    @Ignore
    public int vacancies;

    public Shelter(String name, int capacity, Location location,
                   String address, String notes, String phone, Set<Restriction> restrictions) {
        this.name = name;
        this.capacity = capacity;
        this.location = location;
        this.address = address;
        this.notes = notes;
        this.phone = phone;
        this.restrictions = restrictions;
//        this.reservations = new HashMap<>();
    }

//    public int getTotalReservations() {
//        int totalReservations = 0;
//        for (Integer reservations : reservations.values()) {
//            if (reservations != null) {
//                totalReservations += reservations;
//            }
//        }
//        return totalReservations;
//    }

//    public int getVacancies() {
//        return capacity - getTotalReservations();
//    }

    public double getDistanceInMiles(Location currentLocation) {
        return convertMetersToMiles(location.distanceTo(currentLocation));
    }

    private double convertMetersToMiles(double meters) {
        return meters * METERS_TO_MILES_CONVERSION_FACTOR;
    }

    public String getRestrictionsString() {
        return restrictions.stream().map(Restriction::toString).collect(Collectors.joining(", "));
    }

//    public boolean reserve(User user, int numReservations) {
//        if (reservations.containsKey(user)) {
//            return false;
//        }
//
//        reservations.put(user, numReservations);
//        return true;
//    }

//    public boolean release(User user) {
//        if (!reservations.containsKey(user)) {
//            return false;
//        }
//
//        reservations.remove(user);
//        return true;
//    }

    @Override
    public String toString() {
        return "Shelter{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", capacity=" + capacity +
                ", location=" + location +
                ", address='" + address + '\'' +
                ", notes='" + notes + '\'' +
                ", phone='" + phone + '\'' +
                ", restrictions=" + restrictions +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shelter shelter = (Shelter) o;
        return capacity == shelter.capacity &&
                Objects.equals(name, shelter.name) &&
                Objects.equals(location, shelter.location) &&
                Objects.equals(address, shelter.address) &&
                Objects.equals(notes, shelter.notes) &&
                Objects.equals(phone, shelter.phone) &&
                Objects.equals(restrictions, shelter.restrictions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, capacity, location, address, notes, phone, restrictions);
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.writeLong(id);
        oos.writeObject(name);
        oos.writeInt(capacity);
        oos.writeObject(new Gson().toJson(location));
        oos.writeObject(address);
        oos.writeObject(notes);
        oos.writeObject(phone);
        oos.writeObject(restrictions);
        oos.writeDouble(distance);
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        id = ois.readLong();
        name = (String) ois.readObject();
        capacity = ois.readInt();
        location = new Gson().fromJson((String) ois.readObject(), new TypeToken<Location>() {
        }.getType());
        address = (String) ois.readObject();
        notes = (String) ois.readObject();
        phone = (String) ois.readObject();
        restrictions = (Set<Restriction>) ois.readObject();
        distance = ois.readDouble();
    }
}
