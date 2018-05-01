package com.jgchk.haven.ui.detail;

import com.jgchk.haven.data.DataManager;
import com.jgchk.haven.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class DetailActivityModule {

    @Provides
    DetailViewModel provideDetailViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new DetailViewModel(dataManager, schedulerProvider);
    }
}
