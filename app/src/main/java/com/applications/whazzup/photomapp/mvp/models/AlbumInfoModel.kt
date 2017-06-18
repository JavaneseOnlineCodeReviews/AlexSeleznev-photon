package com.applications.whazzup.photomapp.mvp.models

import com.applications.whazzup.photomapp.data.network.res.user.UserAlbumRes
import io.reactivex.Observable

class AlbumInfoModel : AbstractModel() {
    fun getAlbumById(albumId : String): Observable<UserAlbumRes>{
        return mDataManager.getAlbumById(albumId)
    }
}