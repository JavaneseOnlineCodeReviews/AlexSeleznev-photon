package com.applications.whazzup.photomapp.mvp.models

import android.net.Uri
import com.applications.whazzup.photomapp.data.network.req.AddAlbumReq
import com.applications.whazzup.photomapp.data.network.req.UserChangeInfoReq
import com.applications.whazzup.photomapp.data.network.res.AddAlbumRes
import com.applications.whazzup.photomapp.data.network.res.UserAvatarRes
import com.applications.whazzup.photomapp.data.network.res.user.UserAlbumRes
import com.applications.whazzup.photomapp.data.network.res.user.UserRes
import io.reactivex.Observable
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import java.io.File


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


    fun uploadPhoto(avatar: Uri) : Observable<UserAvatarRes> {
        var file : File = File(avatar.path)
        var sendFile : RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        var body : MultipartBody.Part = MultipartBody.Part.createFormData("image", file.name, sendFile)
        return mDataManager.uploadPhoto(body)
    }

    fun saveAvatarUrl(avatarUrl: String) {
        mDataManager.saveAvatarUrl(avatarUrl)
    }

    fun getUserAvatar() : String{
        return mDataManager.getUserAvatar()
    }

    fun changeUserInfo(userInfo : UserChangeInfoReq): Observable<UserRes>{
        return mDataManager.changeUserInfo(userInfo)
    }

    fun getUserName(): String {
        return mDataManager.mPreferencesManager.getUserName()
    }

    fun getUserLogin(): String {
        return mDataManager.mPreferencesManager.getUserLogin()
    }
}