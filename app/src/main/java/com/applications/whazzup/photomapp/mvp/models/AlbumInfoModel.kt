package com.applications.whazzup.photomapp.mvp.models

import com.applications.whazzup.photomapp.data.network.req.AlbumChangeInfoReq
import com.applications.whazzup.photomapp.data.network.res.user.UserAlbumRes
import io.reactivex.Observable
import retrofit2.Response

class AlbumInfoModel : AbstractModel() {
    fun getAlbumById(albumId : String): Observable<UserAlbumRes>{
        return mDataManager.getAlbumById(albumId)
    }

    fun deletePhotoCard(cardId: String): Observable<Response<Void>> {
        return mDataManager.deletePhotoCard(cardId)
    }

    fun changeAlbumInfo(id: String, req: AlbumChangeInfoReq) : Observable<UserAlbumRes> {
        return mDataManager.changeAlbumInfo(id, req)
    }

    fun deleteAlbum(albumId : String) : Observable<Response<Void>>{
        return mDataManager.deleteAlbum(albumId)
    }
}