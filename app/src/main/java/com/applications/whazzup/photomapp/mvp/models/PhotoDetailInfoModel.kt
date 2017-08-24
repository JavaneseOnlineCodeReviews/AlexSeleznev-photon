package com.applications.whazzup.photomapp.mvp.models

import com.applications.whazzup.photomapp.data.network.res.AddViewRes
import com.applications.whazzup.photomapp.data.network.res.user.UserRes
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class PhotoDetailInfoModel : AbstractModel() {
    fun getUserById(userId : String) : Observable<UserRes> = mDataManager.getUserById(userId)

    fun addView(photocardId: String): Observable<AddViewRes> = mDataManager.addView(photocardId)
    fun addToFavorite(id: String) {
        mDataManager.addToFavorite(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeBy()
    }
}
