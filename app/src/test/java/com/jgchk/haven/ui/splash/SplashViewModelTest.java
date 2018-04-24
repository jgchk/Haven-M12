package com.jgchk.haven.ui.splash;

import android.support.annotation.Nullable;

import com.jgchk.haven.data.DataManager;
import com.jgchk.haven.utils.rx.TestSchedulerProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.schedulers.TestScheduler;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SplashViewModelTest {

    @Nullable
    @Mock
    private SplashNavigator mSplashNavigator;

    @Mock
    private DataManager mMockDataManager;

    @Nullable private SplashViewModel mSplashViewModel;
    @Nullable private TestScheduler mTestScheduler;

    @Before
    public void setUp() {
        mTestScheduler = new TestScheduler();
        TestSchedulerProvider testSchedulerProvider = new TestSchedulerProvider(mTestScheduler);
        mSplashViewModel = new SplashViewModel(mMockDataManager, testSchedulerProvider);
        mSplashViewModel.setNavigator(mSplashNavigator);
    }

    @After
    public void tearDown() {
        mTestScheduler = null;
        mSplashViewModel = null;
        mSplashNavigator = null;
    }

    @Test
    public void splash_savedCredentials_shouldLogin() {
        doReturn(DataManager.LoggedInMode.LOGGED_IN_MODE_SERVER.getType())
                .when(mMockDataManager)
                .getCurrentUserLoggedInMode();

        mSplashViewModel.decideNextActivity();

        verify(mSplashNavigator).openMainActiviy();
    }

    @Test
    public void splash_noSavedCredentials_shouldNotLogin() {
        doReturn(DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT.getType())
                .when(mMockDataManager)
                .getCurrentUserLoggedInMode();

        mSplashViewModel.decideNextActivity();

        verify(mSplashNavigator).openLoginActivity();
    }
}
