package com.applications.whazzup.photomapp.data.managers


import android.content.SharedPreferences

import com.applications.whazzup.photomapp.App

class PreferencesManager {
    var mSharedPreferences: SharedPreferences? = null

    init {
        mSharedPreferences = App.sharedPreferences
    }
}
