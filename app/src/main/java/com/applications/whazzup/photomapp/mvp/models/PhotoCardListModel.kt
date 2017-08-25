package com.applications.whazzup.photomapp.mvp.models

import com.applications.whazzup.photomapp.data.storage.realm.PhotocardRealm
import io.reactivex.Observable


class PhotoCardListModel : AbstractModel(){

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
