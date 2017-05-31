package com.applications.whazzup.photomapp.data.managers


import com.applications.whazzup.photomapp.App
import com.applications.whazzup.photomapp.data.network.RestService
import com.applications.whazzup.photomapp.data.network.res.Photocard
import com.applications.whazzup.photomapp.di.components.DaggerDataManagerComponent
import com.applications.whazzup.photomapp.di.modules.LocalModule
import com.applications.whazzup.photomapp.di.modules.NetworkModule
import io.reactivex.Single
import javax.inject.Inject

class DataManager {

    @Inject
    lateinit var mRestService: RestService

    init {
        DaggerDataManagerComponent.builder()
                .appComponent(App.appComponent)
                .localModule(LocalModule())
                .networkModule(NetworkModule())
                .build().inject(this)
    }

    fun getPhotoCard(limit: Int, offset: Int): Single<List<Photocard>> {
        return mRestService.getPhotoCard(limit, offset)
    }
}
