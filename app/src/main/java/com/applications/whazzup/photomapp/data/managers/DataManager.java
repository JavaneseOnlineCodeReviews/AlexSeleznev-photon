package com.applications.whazzup.photomapp.data.managers;


import com.applications.whazzup.photomapp.App;
import com.applications.whazzup.photomapp.di.components.DaggerDataManagerComponent;
import com.applications.whazzup.photomapp.di.modules.LocalModule;
import com.applications.whazzup.photomapp.di.modules.NetworkModule;

public class DataManager {
    private static DataManager ourInstance = null;

    private DataManager() {
        DaggerDataManagerComponent.builder()
                .appComponent(App.getAppComponent())
                .localModule(new LocalModule())
                .networkModule(new NetworkModule())
                .build().inject(this);
    }

    public static DataManager getInstance(){
        if(ourInstance==null){
            ourInstance=new DataManager();
        }
        return ourInstance;
    }
}
