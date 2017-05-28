package com.applications.whazzup.photomapp.data.managers;


import android.content.SharedPreferences;

import com.applications.whazzup.photomapp.App;

public class PreferencesManager {
    private SharedPreferences mSharedPreferences;

    public PreferencesManager() {
        mSharedPreferences = App.getSharedPreferences();
    }
}
