package com.applications.whazzup.photomapp.mvp.models

import com.applications.whazzup.photomapp.data.storage.realm.UserAlbumRealm
import io.reactivex.Observable


class CardAlbumsModel : AbstractModel() {
    fun getAllAlbumsFromRealm(): Observable<UserAlbumRealm> {
        return mRealmManager.getAllAlbumFromRealm()
    }
}