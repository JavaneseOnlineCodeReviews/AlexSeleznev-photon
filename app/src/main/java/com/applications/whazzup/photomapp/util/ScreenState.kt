package com.applications.whazzup.photomapp.util

import android.support.annotation.LayoutRes
import com.applications.whazzup.photomapp.R

enum class ScreenState(@LayoutRes val layoutId: Int) {
    LOGIN_STATE(R.layout.screen_user_profile),
    IDLE_STATE(R.layout.log_in);
}