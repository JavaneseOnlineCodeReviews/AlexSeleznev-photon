package com.applications.whazzup.photomapp.di.modules

import com.applications.whazzup.photomapp.data.managers.DataManager
import com.applications.whazzup.photomapp.data.managers.RealmManager

import javax.inject.Singleton

import dagger.Module
import dagger.Provides

@Module
class ModelModule {

    @Provides
    @Singleton
    fun provideDataManager(): DataManager {
        return DataManager()
    }

    @Provides
    @Singleton
    fun provideRealmManager(): RealmManager {
        return RealmManager()
    }
}
