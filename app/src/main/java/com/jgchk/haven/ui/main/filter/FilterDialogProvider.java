package com.jgchk.haven.ui.main.filter;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FilterDialogProvider {

    @ContributesAndroidInjector(modules = FilterDialogModule.class)
    abstract FilterDialog provideFilterDialogFactory();
}
