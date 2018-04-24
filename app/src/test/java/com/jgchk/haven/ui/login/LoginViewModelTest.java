package com.jgchk.haven.ui.login;

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

import io.reactivex.Single;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class LoginViewModelTest {

    @Nullable
    @Mock
    private LoginNavigator mLoginNavigator;

    @Mock
    private DataManager mMockDataManager;

    @Nullable private LoginViewModel mLoginViewModel;
    @Nullable private TestScheduler mTestScheduler;

    @Before
    public void setUp() {
        mTestScheduler = new TestScheduler();
        TestSchedulerProvider testSchedulerProvider = new TestSchedulerProvider(mTestScheduler);
        mLoginViewModel = new LoginViewModel(mMockDataManager, testSchedulerProvider);
        mLoginViewModel.setNavigator(mLoginNavigator);
    }

    @After
    public void tearDown() {
        mTestScheduler = null;
        mLoginViewModel = null;
        mLoginNavigator = null;
    }

    @Test
    public void login_correctCredentials_shouldSucceed() {
        User user = TestConstants.getDummyUser();

        doReturn(Single.just(user))
                .when(mMockDataManager)
                .getUserByLogin(user.email, user.password);

        mLoginViewModel.login(user.email, user.password);
        mTestScheduler.triggerActions();

        verify(mLoginNavigator).openMainActivity();
    }

    @Test
    public void login_incorrectEmail_shouldFail() {
        User user = TestConstants.getDummyUser();

        doReturn(Single.error(new NullPointerException("value is null")))
                .when(mMockDataManager)
                .getUserByLogin(user.email, user.password);

        mLoginViewModel.login(user.email, user.password);
        mTestScheduler.triggerActions();

        verify(mLoginNavigator).onFailedLogin();
    }
}
