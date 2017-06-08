package com.applications.whazzup.photomapp.di.modules


import android.content.Context
import com.applications.whazzup.photomapp.di.DaggerScope
import com.applications.whazzup.photomapp.ui.activities.RootActivity

import com.jakewharton.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso

import dagger.Module
import dagger.Provides

@Module
class PicassoCacheModule {
    @Provides
    @DaggerScope(RootActivity::class)
    internal fun providePicasso(context: Context): Picasso {
        val okHttp3Downloader = OkHttp3Downloader(context)
        val picasso = Picasso.Builder(context)
                .downloader(okHttp3Downloader)
                .debugging(true)
                .build()
        return picasso
    }
}
