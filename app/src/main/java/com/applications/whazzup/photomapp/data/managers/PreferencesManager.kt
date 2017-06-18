package com.applications.whazzup.photomapp.data.managers


import android.content.SharedPreferences

import com.applications.whazzup.photomapp.App
import com.applications.whazzup.photomapp.data.network.res.user.UserRes
import com.applications.whazzup.photomapp.util.ConstantManager.USER_AVATAR_KEY
import com.applications.whazzup.photomapp.util.ConstantManager.USER_ID_KEY
import com.applications.whazzup.photomapp.util.ConstantManager.USER_LOGIN_KEY
import com.applications.whazzup.photomapp.util.ConstantManager.USER_NAME_KEY
import com.applications.whazzup.photomapp.util.ConstantManager.USER_TOKEN_KEY

class PreferencesManager {

    var mSharedPreferences: SharedPreferences? = null

    init {
        mSharedPreferences = App.sharedPreferences
    }

    fun saveUserProfileInfo(user: UserRes){
        val editor = mSharedPreferences?.edit()
        editor?.putString(USER_NAME_KEY, user.name)
        editor?.putString(USER_LOGIN_KEY, user.login)
        editor?.putString(USER_TOKEN_KEY, user.token)
        editor?.putString(USER_ID_KEY, user.id)
        editor?.putString(USER_AVATAR_KEY, user.avatar)
        editor?.apply()
    }

    fun getUserId(): String{
       return mSharedPreferences?.getString(USER_ID_KEY, "null") as String
    }

    fun getUserToken(): String{
        return mSharedPreferences?.getString(USER_TOKEN_KEY, "null") as String
    }

    fun getUserLogin(): String{
        return mSharedPreferences?.getString(USER_LOGIN_KEY, "null") as String
    }

    fun getUserName(): String{
        return mSharedPreferences?.getString(USER_NAME_KEY, "null") as String
    }


    fun isUserAuth(): Boolean{
        return mSharedPreferences?.getString(USER_TOKEN_KEY, null)!=null
    }

    fun logOut() {
        val editor = mSharedPreferences?.edit()
        editor?.putString(USER_NAME_KEY, null)
        editor?.putString(USER_LOGIN_KEY, null)
        editor?.putString(USER_TOKEN_KEY, null)
        editor?.putString(USER_ID_KEY, null)
        editor?.apply()
    }

    fun saveUserAvatar(avatarUrl: String) {
        val editor = mSharedPreferences?.edit()
        editor?.putString(USER_AVATAR_KEY, avatarUrl)
        editor?.apply()
    }

    fun getUserAvatar() : String{
        return mSharedPreferences?.getString(USER_AVATAR_KEY, "http://www.jext.org/files/2011/11/photon-2.jpg") as String
    }
}
