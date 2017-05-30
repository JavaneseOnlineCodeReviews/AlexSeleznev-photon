package com.applications.whazzup.photomapp.di.components


import com.applications.whazzup.photomapp.data.managers.DataManager
import com.applications.whazzup.photomapp.di.modules.LocalModule
import com.applications.whazzup.photomapp.di.modules.NetworkModule

import javax.inject.Singleton

import dagger.Component

@Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(NetworkModule::class, LocalModule::class))
@Singleton
interface DataManagerComponent {
    fun inject(manager: DataManager)
}
