package com.applications.whazzup.photomapp.di.modules


import com.applications.whazzup.photomapp.di.DaggerScope
import com.applications.whazzup.photomapp.mvp.models.RootModel
import com.applications.whazzup.photomapp.mvp.presenters.RootPresenter
import com.applications.whazzup.photomapp.ui.activities.RootActivity

import dagger.Module
import dagger.Provides

@Module
class RootModule {
    @Provides
    @DaggerScope(RootActivity::class)
    internal fun provideRootPresenter(): RootPresenter {
        return RootPresenter.INSTANCE
    }

    @Provides
    @DaggerScope(RootActivity::class)
    internal fun provideRootModel(): RootModel {
        return RootModel()
    }
}
