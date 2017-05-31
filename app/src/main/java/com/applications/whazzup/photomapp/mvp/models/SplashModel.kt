package com.applications.whazzup.photomapp.mvp.models

import com.applications.whazzup.photomapp.data.network.res.Photocard
import io.reactivex.Single

/**
 * Created by VZ on 30.05.2017.
 */

class SplashModel : AbstractModel() {
    fun getPhotoCard(limit: Int, offset: Int): Single<List<Photocard>> {
        return mDataManager.getPhotoCard(limit, offset)
    }
}
