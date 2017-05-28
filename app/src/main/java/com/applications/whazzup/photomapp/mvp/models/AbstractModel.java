package com.applications.whazzup.photomapp.mvp.models;


import com.applications.whazzup.photomapp.data.managers.DataManager;
import com.applications.whazzup.photomapp.data.managers.RealmManager;
import com.applications.whazzup.photomapp.di.components.DaggerModelComponent;
import com.applications.whazzup.photomapp.di.modules.ModelModule;

import javax.inject.Inject;

public class AbstractModel {

    @Inject
    DataManager mDataManager;

    @Inject
    RealmManager mRealmManager;

    public AbstractModel() {
        DaggerModelComponent.builder().modelModule(new ModelModule()).build().inject(this);
    }
}
