package com.applications.whazzup.photomapp.mvp.models

import com.applications.whazzup.photomapp.data.network.res.user.UserRes
import io.reactivex.Observable

class AuthorModel : AbstractModel() {
    fun getUserInfoById(userId: String): Observable<UserRes> {
        return mDataManager.getUserById(userId)
    }
}