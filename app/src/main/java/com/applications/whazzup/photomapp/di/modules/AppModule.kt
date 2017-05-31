package com.applications.whazzup.photomapp.di.modules

import android.content.Context

import dagger.Module
import dagger.Provides

@Module
class AppModule(private val mContext: Context) {

    @Provides
    internal fun provideContext(): Context {
        return mContext
    }
}
