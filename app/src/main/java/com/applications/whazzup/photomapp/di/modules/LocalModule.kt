package com.applications.whazzup.photomapp.di.modules


import com.applications.whazzup.photomapp.data.managers.PreferencesManager

import javax.inject.Singleton

import dagger.Module
import dagger.Provides

@Module
class LocalModule {
    @Provides
    @Singleton
    internal fun providePreferencesManager(): PreferencesManager {
        return PreferencesManager()
    }
}
