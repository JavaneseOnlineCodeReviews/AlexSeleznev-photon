package com.applications.whazzup.photomapp.mvp.models

import com.applications.whazzup.photomapp.data.network.req.AddAlbumReq
import com.applications.whazzup.photomapp.data.network.res.AddAlbumRes
import com.applications.whazzup.photomapp.data.network.res.user.UserAlbumRes
import com.applications.whazzup.photomapp.data.network.res.user.UserRes
import io.reactivex.Observable
import retrofit2.Response


class UserProfileModel : AbstractModel() {
    fun  isUserAuth(): Boolean {
        return mDataManager.isUserAuth()
    }

    fun getUserById(): Observable<UserRes>{
        return mDataManager.getUserById(mDataManager.mPreferencesManager.getUserId())
    }

    fun createAlbum(album : AddAlbumReq) : Observable<UserAlbumRes>{
       return mDataManager.createAlbum(album)
    }

    fun deleteUser() : Observable<Response<Void>>{
        return mDataManager.deleteUser()
    }

    fun logOut() {
        mDataManager.mPreferencesManager.logOut()
    }
}