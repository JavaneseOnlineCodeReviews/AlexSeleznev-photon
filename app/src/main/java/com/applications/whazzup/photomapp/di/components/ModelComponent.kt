package com.applications.whazzup.photomapp.di.components


import com.applications.whazzup.photomapp.di.modules.ModelModule
import com.applications.whazzup.photomapp.mvp.models.AbstractModel

import javax.inject.Singleton

import dagger.Component

@Component(modules = arrayOf(ModelModule::class))
@Singleton
interface ModelComponent {
    fun inject(model: AbstractModel)
}
