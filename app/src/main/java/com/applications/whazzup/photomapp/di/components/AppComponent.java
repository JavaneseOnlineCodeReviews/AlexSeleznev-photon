package com.applications.whazzup.photomapp.di.components;


import android.content.Context;

import com.applications.whazzup.photomapp.di.modules.AppModule;

import dagger.Component;

@Component(modules = AppModule.class)
public interface AppComponent {
    Context getContext();
}
