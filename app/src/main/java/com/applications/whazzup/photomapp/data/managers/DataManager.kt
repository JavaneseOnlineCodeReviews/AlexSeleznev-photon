package com.applications.whazzup.photomapp.data.managers


import com.applications.whazzup.photomapp.App
import com.applications.whazzup.photomapp.data.network.RestService
import com.applications.whazzup.photomapp.data.network.req.*
import com.applications.whazzup.photomapp.data.network.res.AddViewRes
import com.applications.whazzup.photomapp.data.network.req.AddAlbumReq
import com.applications.whazzup.photomapp.data.network.req.UserChangeInfoReq
import com.applications.whazzup.photomapp.data.network.req.UserLogInReq
import com.applications.whazzup.photomapp.data.network.req.UserSigInReq
import com.applications.whazzup.photomapp.data.network.req.card_info_req.CardInfoReq
import com.applications.whazzup.photomapp.data.network.res.photocard.PhotocardRes
import com.applications.whazzup.photomapp.data.network.res.UploadPhotoRes
import com.applications.whazzup.photomapp.data.network.res.UserAvatarRes
import com.applications.whazzup.photomapp.data.network.res.user.UserAlbumRes
import com.applications.whazzup.photomapp.data.network.res.user.UserRes
import com.applications.whazzup.photomapp.di.components.DaggerDataManagerComponent
import com.applications.whazzup.photomapp.di.modules.LocalModule
import com.applications.whazzup.photomapp.di.modules.NetworkModule
import io.reactivex.Observable
import okhttp3.MultipartBody
import retrofit2.Response
import javax.inject.Inject

class DataManager {

    @Inject lateinit var mRestService: RestService
    @Inject lateinit var mRealmManager: RealmManager
    @Inject lateinit var mPreferencesManager: PreferencesManager


    init {
        DaggerDataManagerComponent.builder()
                .appComponent(App.appComponent)
                .localModule(LocalModule())
                .networkModule(NetworkModule())
                .build().inject(this)
    }

    companion object {
        val INSTANCE = DataManager()
    }

    fun getPhotoCard(limit: Int, offset: Int): Observable<List<PhotocardRes>> {
        return mRestService.getPhotoCard(limit, offset)
    }

    fun getTagsObs() : Observable<List<String>> {
        return mRestService.getTags()
    }

    fun addView(photocardId: String): Observable<AddViewRes> {
        return mRestService.addView(photocardId)
    }

    fun savePhotocardToRealm(photocardRes: PhotocardRes) {
        mRealmManager.savePhotocardResponseToRealm(photocardRes)
    }

    fun signUpUser(user : UserSigInReq): Observable<UserRes>{
        return mRestService.sigUpUser(user)
    }

    fun logInUser(user : UserLogInReq) : Observable<UserRes>{
        return mRestService.logInUser(user)
    }

    fun isUserAuth() : Boolean{
        return mPreferencesManager.isUserAuth()
    }

    fun saveUserInfo(user: UserRes){
        mPreferencesManager.saveUserProfileInfo(user)
        mRealmManager.clearUserAlbum()
        for(album in user.albums){
            mRealmManager.saveAlbumToRealm(album)
        }

    }

    fun logOut() {
        mPreferencesManager.logOut()
        mRealmManager.clearUserAlbum()
    }

    fun getUserById(userId : String) : Observable<UserRes>{
        return mRestService.getUserById(userId)
    }

    fun createAlbum(album : AddAlbumReq) : Observable<UserAlbumRes>{
        return mRestService.createAlbum(mPreferencesManager.getUserId(), mPreferencesManager.getUserToken(), album)
    }

    fun deleteUser() : Observable<Response<Void>> {
        return mRestService.deleteUser(mPreferencesManager.getUserId(), mPreferencesManager.getUserToken())
    }

    fun changeUserInfo(userInfo: UserChangeInfoReq) : Observable<UserRes>{
        return mRestService.changeUserInfo(mPreferencesManager.getUserId(), mPreferencesManager.getUserToken(), userInfo)
    }

    fun uploadPhoto(file : MultipartBody.Part) : Observable<UserAvatarRes>{
        return mRestService.uploadPhoto(mPreferencesManager.getUserId(), file, mPreferencesManager.getUserToken())
    }

    fun saveAvatarUrl(avatarUrl: String) {
        mPreferencesManager.saveUserAvatar(avatarUrl)
    }

    fun getUserAvatar(): String {
      return  mPreferencesManager.getUserAvatar()
    }

    fun uploadUserPhoto(body: MultipartBody.Part?): Observable<UserAvatarRes> {
        return mRestService.uploadPhoto(mPreferencesManager.getUserId(), body!!, mPreferencesManager.getUserToken());
    }

    fun getAlbumById(albumId : String) : Observable<UserAlbumRes>{
        return mRestService.getAlbumById(mPreferencesManager.getUserId(), albumId)
    }

    fun deletePhotoCard(cardId : String) : Observable<Response<Void>>{
        return mRestService.deletePhotoCard(mPreferencesManager.getUserId(), mPreferencesManager.getUserToken(), cardId)
    }

    fun changeAlbumInfo(albumId : String, albuminfo : AlbumChangeInfoReq) : Observable<UserAlbumRes>{
        return mRestService.changeAlbumInfo(mPreferencesManager.getUserId(), mPreferencesManager.getUserToken(), albumId, albuminfo)
    }

    fun deleteAlbum(albumId: String): Observable<Response<Void>>{
        return mRestService.deleteAlbum(mPreferencesManager.getUserId(), mPreferencesManager.getUserToken(), albumId)
    }

    fun createPhotoCard(cardInfo : CardInfoReq) : Observable<UploadPhotoRes>{
        return mRestService.createPhotoCard(mPreferencesManager.getUserId(), mPreferencesManager.getUserToken(), cardInfo)
    }
}
