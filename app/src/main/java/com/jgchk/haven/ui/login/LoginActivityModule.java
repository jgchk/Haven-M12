package com.jgchk.haven.ui.login;

import com.jgchk.haven.data.DataManager;
import com.jgchk.haven.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class LoginActivityModule {

    @Provides
    LoginViewModel provideLoginViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new LoginViewModel(dataManager, schedulerProvider);
    }
}
