package com.applications.whazzup.photomapp.mvp.models


import com.applications.whazzup.photomapp.data.managers.DataManager
import com.applications.whazzup.photomapp.data.managers.RealmManager
import com.applications.whazzup.photomapp.di.components.DaggerModelComponent
import com.applications.whazzup.photomapp.di.modules.ModelModule
import javax.inject.Inject

abstract class AbstractModel {
    @Inject
    lateinit var mDataManager: DataManager

    @Inject
    lateinit var mRealmManager: RealmManager

    init {
        DaggerModelComponent.builder().modelModule(ModelModule()).build().inject(this)
    }
}
