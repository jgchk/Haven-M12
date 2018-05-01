package com.jgchk.haven.di.module;

import android.app.Application;
import android.content.Context;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;
import com.jgchk.haven.R;
import com.jgchk.haven.data.AppDataManager;
import com.jgchk.haven.data.DataManager;
import com.jgchk.haven.data.local.db.AppDatabase;
import com.jgchk.haven.data.local.db.AppDbHelper;
import com.jgchk.haven.data.local.db.DbHelper;
import com.jgchk.haven.data.local.location.AppLocationHelper;
import com.jgchk.haven.data.local.location.LocationHelper;
import com.jgchk.haven.data.local.prefs.AppPreferencesHelper;
import com.jgchk.haven.data.local.prefs.PreferencesHelper;
import com.jgchk.haven.di.DatabaseInfo;
import com.jgchk.haven.di.PreferenceInfo;
import com.jgchk.haven.utils.AppConstants;
import com.jgchk.haven.utils.rx.AppSchedulerProvider;
import com.jgchk.haven.utils.rx.SchedulerProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

import static com.jgchk.haven.utils.AppConstants.GSON;

@SuppressWarnings("TypeMayBeWeakened")
@Module
public class AppModule {

    @Provides
    @Singleton
    AppDatabase provideAppDatabase(Context context, @DatabaseInfo String dbName) {
        return AppDatabase.getInstance(context, dbName);
    }

    @Provides
    @Singleton
    CalligraphyConfig provideCalligraphyDefaultConfig() {
        return new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/source-sans-pro/SourceSansPro-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build();
    }

    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(AppDataManager appDataManager) {
        return appDataManager;
    }

    @Provides
    @DatabaseInfo
    String provideDatabaseName() {
        return AppConstants.DB_NAME;
    }

    @Provides
    @Singleton
    DbHelper provideDbHelper(AppDbHelper appDbHelper) {
        return appDbHelper;
    }

    @Provides
    @Singleton
    PreferencesHelper providePreferencesHelper(AppPreferencesHelper appPreferencesHelper) {
        return appPreferencesHelper;
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return GSON;
    }

    @Provides
    @PreferenceInfo
    String providePreferenceName() {
        return AppConstants.PREF_NAME;
    }

    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }

    @Provides
    @Singleton
    FusedLocationProviderClient provideFusedLocationProviderClient(Context context) {
        return LocationServices.getFusedLocationProviderClient(context);
    }

    @Provides
    @Singleton
    LocationHelper provideLocationHelper(AppLocationHelper appLocationHelper) {
        return appLocationHelper;
    }
}
