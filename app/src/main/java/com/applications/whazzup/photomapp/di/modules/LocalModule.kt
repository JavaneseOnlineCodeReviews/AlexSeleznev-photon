package com.applications.whazzup.photomapp.di.modules


import com.applications.whazzup.photomapp.data.managers.PreferencesManager
import com.applications.whazzup.photomapp.data.managers.RealmManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocalModule {
    @Provides
    @Singleton
    internal fun providePreferencesManager(): PreferencesManager {
        return PreferencesManager()
    }

    @Provides
    @Singleton
    internal fun provideRealmManager(): RealmManager {
        return RealmManager()
    }

}
