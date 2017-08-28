package com.applications.whazzup.photomapp.util

import android.content.Context
import android.net.ConnectivityManager
import com.applications.whazzup.photomapp.App
import io.reactivex.Observable

class NetworkStatusChecker {

    fun isNetworkAvailable(): Boolean {
        val cm : ConnectivityManager = App
                .applicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var activeNetwork = cm.activeNetworkInfo

        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }

    fun isInternetAvailable(): Observable<Boolean> {
        return Observable.just(isNetworkAvailable())
    }
}