package com.jgchk.haven.ui.main;

import android.arch.lifecycle.ViewModelProvider;
import android.support.v7.widget.LinearLayoutManager;

import com.jgchk.haven.ViewModelProviderFactory;
import com.jgchk.haven.data.DataManager;
import com.jgchk.haven.ui.main.results.ResultsAdapter;
import com.jgchk.haven.utils.rx.SchedulerProvider;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule {

    @Provides
    MainViewModel provideMainViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new MainViewModel(dataManager, schedulerProvider);
    }

    @Provides
    @Named("MainViewModel")
    ViewModelProvider.Factory mainViewModelProvider(MainViewModel mainViewModel) {
        return new ViewModelProviderFactory<>(mainViewModel);
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(MainActivity activity) {
        return new LinearLayoutManager(activity);
    }

    @Provides
    ResultsAdapter provideResultsAdapter() {
        return new ResultsAdapter();
    }
}
