package com.applications.whazzup.photomapp.mvp.models

import com.applications.whazzup.photomapp.data.network.res.photocard.PhotocardRes
import com.applications.whazzup.photomapp.data.network.res.user.UserAlbumRes
import com.applications.whazzup.photomapp.data.network.res.user.UserRes
import com.applications.whazzup.photomapp.data.storage.realm.PhotocardRealm
import io.reactivex.Observable

class SplashModel : AbstractModel() {
    fun getPhotoCard(limit: Int, offset: Int): Observable<List<PhotocardRes>> {
        return mDataManager.getPhotoCard(limit, offset)
    }

    fun savePhotoCardToRealm(photoCardRes: PhotocardRes) {
        mDataManager.savePhotocardToRealm(photoCardRes)
    }

    fun getUserById() : Observable<UserRes>{
        return mDataManager.getUserById(mDataManager.mPreferencesManager.getUserId())
    }

    fun saveAlbumToRealm(it: UserAlbumRes) {
        mRealmManager.saveAlbumToRealm(it)
    }

    fun getCardObs():Observable<PhotocardRealm>{
        return Observable.mergeDelayError(fromDisk(), fromNetwork())
                .distinct(PhotocardRealm::id)
    }


    fun fromNetwork() : Observable<PhotocardRealm>{
        return mDataManager.getCardObsFromNetwork()
    }

    fun fromDisk():Observable<PhotocardRealm>{
        return mRealmManager.getAllCardFromRealm()
    }
}
