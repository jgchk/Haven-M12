package com.jgchk.haven.ui.registration;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.jgchk.haven.R;
import com.jgchk.haven.data.model.db.User;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
public class RegistrationActivityTest {

    @Rule
    public ActivityTestRule<RegistrationActivity> mActivityRule = new ActivityTestRule<>(RegistrationActivity.class);

    @Test
    public void checkViewsDisplay() throws InterruptedException {
        onView(withId(R.id.activityMain)).check(matches(isDisplayed()));
        onView(withId(R.id.etEmail)).check(matches(isDisplayed()))
                .perform(closeSoftKeyboard());
        onView(withId(R.id.etPassword)).check(matches(isDisplayed()));
        onView(withId(R.id.spnAccountType)).check(matches(isDisplayed()));
        for (User.AccountType accountType : User.AccountType.values()) {
            onView(withId(R.id.spnAccountType)).perform(click());
            onData(allOf(is(instanceOf(User.AccountType.class)), is(accountType)))
                    .perform(click());
            onView(withId(R.id.spnAccountType))
                    .check(matches(withSpinnerText(accountType.toString())));
            Thread.sleep(100);
        }
        onView(withId(R.id.btnRegister)).check(matches(isDisplayed()));
        onView(withText(R.string.registration_btnRegister_text)).check(matches(isDisplayed()));
        onView(withId(R.id.tvLogin)).check(matches(isDisplayed()));
        onView(withText(R.string.registration_tvLogin_text)).check(matches(isDisplayed()));
    }
}
