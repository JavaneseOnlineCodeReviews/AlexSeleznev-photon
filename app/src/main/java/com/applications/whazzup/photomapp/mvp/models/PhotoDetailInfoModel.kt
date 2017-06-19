package com.applications.whazzup.photomapp.mvp.models

import com.applications.whazzup.photomapp.data.network.res.user.UserRes
import io.reactivex.Observable

class PhotoDetailInfoModel : AbstractModel() {
    fun getUserById(userId : String) : Observable<UserRes> = mDataManager.getUserById(userId)
}
