package com.jgchk.haven.ui.registration;

import com.jgchk.haven.data.DataManager;
import com.jgchk.haven.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class RegistrationActivityModule {

    @Provides
    RegistrationViewModel provideRegistrationViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new RegistrationViewModel(dataManager, schedulerProvider);
    }
}
