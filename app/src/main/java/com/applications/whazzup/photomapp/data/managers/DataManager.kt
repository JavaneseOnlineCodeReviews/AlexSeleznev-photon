package com.applications.whazzup.photomapp.data.managers


import com.applications.whazzup.photomapp.App
import com.applications.whazzup.photomapp.data.network.RestService
import com.applications.whazzup.photomapp.data.network.req.UserLogInReq
import com.applications.whazzup.photomapp.data.network.req.UserSigInReq
import com.applications.whazzup.photomapp.data.network.res.PhotocardRes
import com.applications.whazzup.photomapp.data.network.res.user.UserRes
import com.applications.whazzup.photomapp.di.components.DaggerDataManagerComponent
import com.applications.whazzup.photomapp.di.modules.LocalModule
import com.applications.whazzup.photomapp.di.modules.NetworkModule
import io.reactivex.Observable
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

    fun getPhotoCard(limit: Int, offset: Int): Observable<List<PhotocardRes>> {
        return mRestService.getPhotoCard(limit, offset)
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
    }

    fun logOut() {
        mPreferencesManager.logOut()
    }

    fun getUserById(userId : String) : Observable<UserRes>{
        return mRestService.getUserById(userId)
    }
}
