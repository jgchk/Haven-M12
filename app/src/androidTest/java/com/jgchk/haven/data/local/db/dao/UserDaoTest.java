package com.jgchk.haven.data.local.db.dao;

import android.arch.persistence.room.Room;
import android.database.sqlite.SQLiteConstraintException;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.jgchk.haven.data.local.db.AppDatabase;
import com.jgchk.haven.data.local.db.dao.UserDao;
import com.jgchk.haven.data.model.db.User;
import com.jgchk.haven.utils.TestConstants;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(AndroidJUnit4.class)
public class UserDaoTest {

    private AppDatabase mDb;
    private UserDao mUserDao;

    private final User user = TestConstants.getDummyUser();

    @Before
    public void setUp() {
        mDb = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                AppDatabase.class).build();
        mUserDao = mDb.userDao();
    }

    @After
    public void tearDown() {
        mDb.close();
    }

    @Test
    public void delete_existingUser_shouldDelete() {
        // Setup
        mUserDao.insert(user);

        // Execute
        mUserDao.delete(user);

        // Check
        assertNull(mUserDao.findByEmail(user.email));
    }

    @Test
    public void findByEmail_existingUser_shouldFindUser() {
        // Setup
        mUserDao.insert(user);

        // Check
        assertEquals(user, mUserDao.findByEmail(user.email));
    }

    @Test
    public void findByEmail_nonexistingUser_shouldNotFindUser() {
        assertNull(mUserDao.findByEmail(TestConstants.ALT_EMAIL));
    }

    @Test
    public void findByLogin_existingUser_shouldFindUser() {
        // Setup
        mUserDao.insert(user);

        // Check
        assertEquals(user, mUserDao.findByLogin(user.email, user.password));
    }

    @Test
    public void findByLogin_incorrectEmail_shouldNotFindUser() {
        // Setup
        mUserDao.insert(user);

        // Check
        assertNull(mUserDao.findByLogin(TestConstants.ALT_EMAIL, user.password));
    }

    @Test
    public void findByLogin_incorrectPassword_shouldNotFindUser() {
        // Setup
        mUserDao.insert(user);

        // Check
        assertNull(mUserDao.findByLogin(user.email, TestConstants.ALT_PASSWORD));
    }

    @Test
    public void findByLogin_incorrectEmailAndPassword_shouldNotFindUser() {
        // Setup
        mUserDao.insert(user);

        // Check
        assertNull(mUserDao.findByLogin(TestConstants.ALT_EMAIL, TestConstants.ALT_PASSWORD));
    }

    @Test
    public void insert_nonexistingUser_shouldInsert() {
        // Execute
        mUserDao.insert(user);

        // Check
        mUserDao.findByEmail(user.email);
    }

    @Test(expected = SQLiteConstraintException.class)
    public void insert_existingUser_shouldFail() {
        // Setup
        mUserDao.insert(user);

        // Execute
        mUserDao.insert(user);
    }
}
