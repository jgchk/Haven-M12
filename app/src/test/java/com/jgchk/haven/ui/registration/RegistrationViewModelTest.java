package com.jgchk.haven.ui.registration;

import android.database.sqlite.SQLiteConstraintException;
import android.support.annotation.Nullable;

import com.jgchk.haven.data.DataManager;
import com.jgchk.haven.data.model.db.User;
import com.jgchk.haven.utils.TestConstants;
import com.jgchk.haven.utils.rx.TestSchedulerProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Observable;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationViewModelTest {

    @Nullable
    @Mock
    private RegistrationNavigator mRegistrationNavigator;

    @Mock
    private DataManager mMockDataManager;

    @Nullable private RegistrationViewModel mRegistrationViewModel;
    @Nullable private TestScheduler mTestScheduler;

    @Before
    public void setUp() {
        mTestScheduler = new TestScheduler();
        TestSchedulerProvider testSchedulerProvider = new TestSchedulerProvider(mTestScheduler);
        mRegistrationViewModel = new RegistrationViewModel(mMockDataManager, testSchedulerProvider);
        mRegistrationViewModel.setNavigator(mRegistrationNavigator);
    }

    @After
    public void tearDown() {
        mTestScheduler = null;
        mRegistrationViewModel = null;
        mRegistrationNavigator = null;
    }

    @Test
    public void registration_newCredentials_shouldSucceed() {
        User user = TestConstants.getDummyUser();

        doReturn(Observable.just(true))
                .when(mMockDataManager)
                .insertUser(user);

        mRegistrationViewModel.register(user.email, user.password, user.accountType);
        mTestScheduler.triggerActions();

        verify(mRegistrationNavigator).openMainActivity();
    }

    @Test
    public void registration_duplicateCredentials_shouldFail() {
        User user = TestConstants.getDummyUser();

        doReturn(Observable.error(new SQLiteConstraintException()))
                .when(mMockDataManager)
                .insertUser(user);

        mRegistrationViewModel.register(user.email, user.password, user.accountType);
        mTestScheduler.triggerActions();

        verify(mRegistrationNavigator).onFailedRegistration();
    }
}
