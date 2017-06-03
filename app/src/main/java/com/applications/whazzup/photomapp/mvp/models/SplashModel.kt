package com.applications.whazzup.photomapp.mvp.models

import com.applications.whazzup.photomapp.data.network.res.Photocard
import io.reactivex.Single

class SplashModel : AbstractModel() {
    fun getPhotoCard(limit: Int, offset: Int): Single<List<Photocard>> {
        return mDataManager.getPhotoCard(limit, offset)
    }
}
