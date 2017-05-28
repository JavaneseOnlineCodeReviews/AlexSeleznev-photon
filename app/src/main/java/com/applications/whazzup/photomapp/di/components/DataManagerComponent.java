package com.applications.whazzup.photomapp.di.components;


import com.applications.whazzup.photomapp.data.managers.DataManager;
import com.applications.whazzup.photomapp.di.modules.LocalModule;
import com.applications.whazzup.photomapp.di.modules.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;

@Component(dependencies = AppComponent.class, modules = {NetworkModule.class, LocalModule.class})
@Singleton
public interface DataManagerComponent {
    void inject (DataManager manager);
}
