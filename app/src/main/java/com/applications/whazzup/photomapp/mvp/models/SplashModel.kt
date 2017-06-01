package com.applications.whazzup.photomapp.mvp.models

import com.applications.whazzup.photomapp.data.network.res.PhotocardRes
import io.reactivex.Observable

/**
 * Created by VZ on 30.05.2017.
 */

class SplashModel : AbstractModel() {
    fun getPhotoCard(limit: Int, offset: Int): Observable<List<PhotocardRes>> {
        return mDataManager.getPhotoCard(limit, offset)
    }

    fun savePhotocardToRealm(photocardRes: PhotocardRes) {
        mDataManager.savePhotocardToRealm(photocardRes)
    }
}
