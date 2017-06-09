package com.applications.whazzup.photomapp.mvp.models


class UserProfileModel : AbstractModel() {
    fun  isUserAuth(): Boolean {
        return mDataManager.isUserAuth()
    }
}