package com.applications.whazzup.photomapp.di.modules


import com.applications.whazzup.photomapp.di.scopes.RootScope
import com.applications.whazzup.photomapp.mvp.models.RootModel
import com.applications.whazzup.photomapp.mvp.presenters.RootPresenter

import dagger.Module
import dagger.Provides

@Module
class RootModule {
    @Provides
    @RootScope
    internal fun provideRootPresenter(): RootPresenter {
        return RootPresenter.INSTANCE
    }

    @Provides
    @RootScope
    internal fun provideRootModel(): RootModel {
        return RootModel()
    }
}
