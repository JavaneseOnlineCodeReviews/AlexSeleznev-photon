package com.applications.whazzup.photomapp.di.modules;


import com.applications.whazzup.photomapp.data.managers.PreferencesManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class LocalModule {
    @Provides
    @Singleton
    PreferencesManager providePreferencesManager(){
        return new PreferencesManager();
    }
}
