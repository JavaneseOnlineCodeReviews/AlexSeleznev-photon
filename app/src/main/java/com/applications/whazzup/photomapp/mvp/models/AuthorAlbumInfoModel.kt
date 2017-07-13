package com.applications.whazzup.photomapp.mvp.models

import com.applications.whazzup.photomapp.data.network.res.user.UserAlbumRes
import io.reactivex.Observable


class AuthorAlbumInfoModel : AbstractModel() {

    fun getAlbumById(id: String) : Observable<UserAlbumRes> {
        return mDataManager.getAlbumById(id)
    }
}