package com.applications.whazzup.photomapp.data.network


import android.util.Log
import com.applications.whazzup.photomapp.util.ConstantManager
import com.applications.whazzup.photomapp.util.NetworkStatusChecker
import io.reactivex.Observable
import retrofit2.Response

class RestCallTransformer<R> : rx.Observable.Transformer<Response<R>, R> {
    override fun call(t: rx.Observable<Response<R>>?): rx.Observable<R> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}