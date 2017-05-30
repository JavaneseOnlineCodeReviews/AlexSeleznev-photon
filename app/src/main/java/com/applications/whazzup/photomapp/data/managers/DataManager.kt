package com.applications.whazzup.photomapp.data.managers


import com.applications.whazzup.photomapp.App
import com.applications.whazzup.photomapp.di.components.DaggerDataManagerComponent
import com.applications.whazzup.photomapp.di.modules.LocalModule
import com.applications.whazzup.photomapp.di.modules.NetworkModule

object DataManager {
    init {
        DaggerDataManagerComponent.builder()
                .appComponent(App.appComponent)
                .localModule(LocalModule())
                .networkModule(NetworkModule())
                .build().inject(this)
    }
}
