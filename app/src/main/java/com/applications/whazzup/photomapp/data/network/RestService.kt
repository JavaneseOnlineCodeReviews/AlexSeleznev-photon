package com.applications.whazzup.photomapp.data.network

import com.applications.whazzup.photomapp.data.network.res.Photocard
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface RestService {

    @GET("photocard/list")
    fun getPhotoCard(@Query("limit") limit: Int, @Query("offset") offset: Int): Single<List<Photocard>>

}
