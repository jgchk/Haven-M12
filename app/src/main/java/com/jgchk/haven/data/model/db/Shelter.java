package com.jgchk.haven.data.model.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.location.Location;

import com.jgchk.haven.data.model.others.Restriction;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Entity(tableName = "shelters")
public class Shelter {

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

    public Map<User, Integer> reservations;

    public double distance;

    public Shelter(String name, int capacity, Location location,
                   String address, String notes, String phone, Set<Restriction> restrictions) {
        this.name = name;
        this.capacity = capacity;
        this.location = location;
        this.address = address;
        this.notes = notes;
        this.phone = phone;
        this.restrictions = restrictions;
        this.reservations = new HashMap<>();
    }

    public int getTotalReservations() {
        int totalReservations = 0;
        for (Integer reservations : reservations.values()) {
            if (reservations != null) {
                totalReservations += reservations;
            }
        }
        return totalReservations;
    }

    public int getVacancies() {
        return capacity - getTotalReservations();
    }

    public double getDistanceInMiles(Location currentLocation) {
        return convertMetersToMiles(location.distanceTo(currentLocation));
    }

    private double convertMetersToMiles(double meters) {
        return meters * METERS_TO_MILES_CONVERSION_FACTOR;
    }

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
                ", reservations=" + reservations +
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
}
