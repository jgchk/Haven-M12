package com.jgchk.haven.data.local.db.dao;

import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.jgchk.haven.data.local.db.AppDatabase;
import com.jgchk.haven.data.model.db.Shelter;
import com.jgchk.haven.utils.TestConstants;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class ShelterDaoTest {

    private AppDatabase mDb;
    private ShelterDao mShelterDao;

    @Before
    public void setUp() {
        mDb = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                AppDatabase.class).build();
        mShelterDao = mDb.shelterDao();
    }

    @After
    public void tearDown() {
        mDb.close();
    }

    @Test
    public void loadAll_shouldSucceed() {
        // Setup
        Shelter shelter = TestConstants.getDummyShelter();
        List<Shelter> shelters = new ArrayList<>();
        shelters.add(shelter);
        mShelterDao.insert(shelter);

        // Check
        assertEquals(shelters, mShelterDao.loadAll());
    }
}
