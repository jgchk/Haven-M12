package com.jgchk.haven.di.builder;

import com.jgchk.haven.ui.detail.DetailActivity;
import com.jgchk.haven.ui.detail.DetailActivityModule;
import com.jgchk.haven.ui.login.LoginActivity;
import com.jgchk.haven.ui.login.LoginActivityModule;
import com.jgchk.haven.ui.main.MainActivity;
import com.jgchk.haven.ui.main.MainActivityModule;
import com.jgchk.haven.ui.main.filter.FilterDialogProvider;
import com.jgchk.haven.ui.registration.RegistrationActivity;
import com.jgchk.haven.ui.registration.RegistrationActivityModule;
import com.jgchk.haven.ui.splash.SplashActivity;
import com.jgchk.haven.ui.splash.SplashActivityModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = {SplashActivityModule.class})
    abstract SplashActivity bindSplashActivity();

    @ContributesAndroidInjector(modules = {LoginActivityModule.class})
    abstract LoginActivity bindLoginActivity();

    @ContributesAndroidInjector(modules = {RegistrationActivityModule.class})
    abstract RegistrationActivity bindRegistrationActivity();

    @ContributesAndroidInjector(modules = {MainActivityModule.class, FilterDialogProvider.class})
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector(modules = {DetailActivityModule.class})
    abstract DetailActivity bindDetailActivity();
}
