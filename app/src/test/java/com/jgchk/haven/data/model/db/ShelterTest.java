package com.jgchk.haven.data.model.db;

import android.location.Location;

import com.jgchk.haven.data.model.others.Restriction;
import com.jgchk.haven.utils.TestConstants;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ShelterTest {

    @Test
    public void equals_equalShelters_shouldReturnTrue() {
        assertEquals(TestConstants.getDummyShelter(), TestConstants.getDummyShelter());
    }

    @Test
    public void equals_unequalIds_shouldReturnTrue() {
        Shelter shelterA = TestConstants.getDummyShelter();
        Shelter shelterB = TestConstants.getDummyShelter();
        shelterB.id = 123L;
        assertEquals(shelterA, shelterB);
    }

    @Test
    public void equals_unequalNames_shouldReturnFalse() {
        Shelter shelterA = TestConstants.getDummyShelter();
        Shelter shelterB = TestConstants.getDummyShelter();
        shelterB.name = "test";
        assertNotEquals(shelterA, shelterB);
    }

    @Test
    public void equals_unequalCapacities_shouldReturnFalse() {
        Shelter shelterA = TestConstants.getDummyShelter();
        Shelter shelterB = TestConstants.getDummyShelter();
        shelterB.capacity = 1;
        assertNotEquals(shelterA, shelterB);
    }

    @Test
    public void equals_unequalLocation_shouldReturnFalse() {
        Shelter shelterA = TestConstants.getDummyShelter();
        Shelter shelterB = TestConstants.getDummyShelter();
        shelterB.location = new Location("test");
        assertNotEquals(shelterA, shelterB);
    }

    @Test
    public void equals_unequalAddress_shouldReturnFalse() {
        Shelter shelterA = TestConstants.getDummyShelter();
        Shelter shelterB = TestConstants.getDummyShelter();
        shelterB.address = "test";
        assertNotEquals(shelterA, shelterB);
    }

    @Test
    public void equals_unequalNotes_shouldReturnFalse() {
        Shelter shelterA = TestConstants.getDummyShelter();
        Shelter shelterB = TestConstants.getDummyShelter();
        shelterB.notes = "test";
        assertNotEquals(shelterA, shelterB);
    }

    @Test
    public void equals_unequalPhone_shouldReturnFalse() {
        Shelter shelterA = TestConstants.getDummyShelter();
        Shelter shelterB = TestConstants.getDummyShelter();
        shelterB.phone = "test";
        assertNotEquals(shelterA, shelterB);
    }

    @Test
    public void equals_unequalRestrictions_shouldReturnFalse() {
        Shelter shelterA = TestConstants.getDummyShelter();
        Shelter shelterB = TestConstants.getDummyShelter();
        shelterB.restrictions = new HashSet<>(Arrays.asList(Restriction.values()));
        assertNotEquals(shelterA, shelterB);
    }
}
