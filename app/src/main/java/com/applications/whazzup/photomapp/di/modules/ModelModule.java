package com.applications.whazzup.photomapp.di.modules;

import com.applications.whazzup.photomapp.data.managers.DataManager;
import com.applications.whazzup.photomapp.data.managers.RealmManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ModelModule {

    @Provides
    @Singleton
    DataManager provideDataManager(){
        return DataManager.getInstance();
    }

    @Provides
    @Singleton
    RealmManager provideRealmManager(){
        return new RealmManager();
    }
}
