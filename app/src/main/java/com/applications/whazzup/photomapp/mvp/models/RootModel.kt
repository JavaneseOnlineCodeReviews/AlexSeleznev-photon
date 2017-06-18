package com.applications.whazzup.photomapp.mvp.models

import com.applications.whazzup.photomapp.data.network.req.UserChangeInfoReq
import com.applications.whazzup.photomapp.data.network.req.UserLogInReq
import com.applications.whazzup.photomapp.data.network.req.UserSigInReq
import com.applications.whazzup.photomapp.data.network.res.user.UserRes
import com.applications.whazzup.photomapp.data.storage.dto.PhotoCardDto
import io.reactivex.Observable


class RootModel : AbstractModel() {

    var cardList : List<PhotoCardDto> = ArrayList()

    fun addToCardList(cardDto:PhotoCardDto){
        (cardList as ArrayList).add(cardDto)
    }

    fun signUpUser(user : UserSigInReq) : Observable<UserRes>{
        return mDataManager.signUpUser(user);
    }

    fun logInUser(user: UserLogInReq) : Observable<UserRes>{
        return mDataManager.logInUser(user)
    }

    fun isUserAuth(): Boolean{
        return mDataManager.isUserAuth()
    }

    fun saveUserInfo(user: UserRes){
        mDataManager.saveUserInfo(user)
    }

    fun logOut() {
        mDataManager.logOut()
    }

    fun changeUserInfo(userInfo : UserChangeInfoReq): Observable<UserRes>{
        return mDataManager.changeUserInfo(userInfo)
    }

    fun getUserAvatar(): String {
        return mDataManager.mPreferencesManager.getUserAvatar()
    }
}
