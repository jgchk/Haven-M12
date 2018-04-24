package com.jgchk.haven.data.local.db;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.jgchk.haven.data.model.db.Shelter;
import com.jgchk.haven.utils.AppLogger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class AppDatabaseTest {

    private AppDatabase mDb;

    @Before
    public void setUp() {
        mDb = AppDatabase.getTestInstance(InstrumentationRegistry.getTargetContext());
    }

    @After
    public void tearDown() {
        mDb.close();
    }

    @Test
    public void appDatabase_newDatabase_shouldAutoPopulate() {
        Shelter[] shelters_shouldContain = ShelterDatabasePopulator.populateShelterDatabase(InstrumentationRegistry.getTargetContext());
        AppLogger.d(Arrays.toString(shelters_shouldContain));
        List<Shelter> shelters_actual = mDb.shelterDao().loadAll();
        for (Shelter shelter : shelters_shouldContain) {
            AppLogger.d("Should contain: " + shelter);
            assertTrue(shelters_actual.contains(shelter));
        }
    }
}
