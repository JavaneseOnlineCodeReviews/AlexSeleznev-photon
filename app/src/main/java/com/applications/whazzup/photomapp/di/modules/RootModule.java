package com.applications.whazzup.photomapp.di.modules;


import com.applications.whazzup.photomapp.di.scopes.RootScope;
import com.applications.whazzup.photomapp.mvp.models.RootModel;
import com.applications.whazzup.photomapp.mvp.presenters.RootPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class RootModule {
    @Provides
    @RootScope
    RootPresenter provideRootPresenter(){
        return RootPresenter.getInstance();
    }

    @Provides
    @RootScope
    RootModel provideRootModel(){
        return new RootModel();
    }
}
