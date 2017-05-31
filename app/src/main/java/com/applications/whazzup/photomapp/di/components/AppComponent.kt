package com.applications.whazzup.photomapp.di.components


import android.content.Context

import com.applications.whazzup.photomapp.di.modules.AppModule

import dagger.Component

@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
    val context: Context
}
