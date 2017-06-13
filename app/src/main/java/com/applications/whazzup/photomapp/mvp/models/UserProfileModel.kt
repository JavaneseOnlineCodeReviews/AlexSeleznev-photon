package com.applications.whazzup.photomapp.mvp.models

import com.applications.whazzup.photomapp.data.network.res.user.UserRes
import io.reactivex.Observable


class UserProfileModel : AbstractModel() {
    fun  isUserAuth(): Boolean {
        return mDataManager.isUserAuth()
    }

    fun getUserById(): Observable<UserRes>{
        return mDataManager.getUserById(mDataManager.mPreferencesManager.getUserId())
    }
}