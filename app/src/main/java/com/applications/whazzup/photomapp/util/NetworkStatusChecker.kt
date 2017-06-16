package com.applications.whazzup.photomapp.util

import android.content.Context
import com.applications.whazzup.photomapp.App
import io.reactivex.Observable

class NetworkStatusChecker {
    fun isNetworkAvailable(): Boolean {
        val activeNetwork = App
                .applicationContezt()
                .getSystemService(Context.CONNECTIVITY_SERVICE)
                .activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }

    fun isInternetAvailable(): Observable<Boolean> {
        return Observable.just(isNetworkAvailable())
    }
}