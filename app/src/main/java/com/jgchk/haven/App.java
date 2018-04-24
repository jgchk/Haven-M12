package com.jgchk.haven;

import android.app.Activity;
import android.app.Application;

import com.jgchk.haven.di.component.DaggerAppComponent;
import com.jgchk.haven.utils.AppLogger;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class App extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> mDispatchingAndroidInjector;

    @Inject
    CalligraphyConfig mCalligraphyConfig;

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return mDispatchingAndroidInjector;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        DaggerAppComponent
                .builder()
                .application(this)
                .build()
                .inject(this);

        AppLogger.init();

        CalligraphyConfig.initDefault(mCalligraphyConfig);
    }

    private boolean isTestMode() {
        try {
            this.getClassLoader().loadClass("com.jgchk.haven.ui.login.TestIndicator");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}
