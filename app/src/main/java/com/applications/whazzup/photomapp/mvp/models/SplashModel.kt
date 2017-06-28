package com.applications.whazzup.photomapp.mvp.models

import com.applications.whazzup.photomapp.data.network.res.PhotocardRes
import com.applications.whazzup.photomapp.data.network.res.user.UserAlbumRes
import com.applications.whazzup.photomapp.data.network.res.user.UserRes
import io.reactivex.Observable

class SplashModel : AbstractModel() {
    fun getPhotoCard(limit: Int, offset: Int): Observable<List<PhotocardRes>> {
        return mDataManager.getPhotoCard(limit, offset)
    }

    fun savePhotocardToRealm(photocardRes: PhotocardRes) {
        mDataManager.savePhotocardToRealm(photocardRes)
    }

    fun getUserById() : Observable<UserRes>{
        return mDataManager.getUserById(mDataManager.mPreferencesManager.getUserId())
    }

    fun saveAlbumToRealm(it: UserAlbumRes) {
        mRealmManager.saveAlbumToRealm(it)
    }
}
