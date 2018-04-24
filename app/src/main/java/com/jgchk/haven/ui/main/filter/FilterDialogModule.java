package com.jgchk.haven.ui.main.filter;

import com.jgchk.haven.data.DataManager;
import com.jgchk.haven.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class FilterDialogModule {

    @Provides
    FilterViewModel provideFilterViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new FilterViewModel(dataManager, schedulerProvider);
    }
}
