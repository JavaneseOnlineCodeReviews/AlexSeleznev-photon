package com.applications.whazzup.photomapp.data.network

import com.applications.whazzup.photomapp.data.network.req.UserLogInReq
import com.applications.whazzup.photomapp.data.network.req.UserSigInReq
import com.applications.whazzup.photomapp.data.network.res.PhotocardRes
import com.applications.whazzup.photomapp.data.network.res.user.UserRes
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface RestService {

    @GET("photocard/list")
    fun getPhotoCard(@Query("limit") limit: Int, @Query("offset") offset: Int): Observable<List<PhotocardRes>>

    @POST("user/signUp")
    fun sigUpUser(@Body user : UserSigInReq): Observable<UserRes>


    @POST("user/signIn")
    fun logInUser(@Body user: UserLogInReq) : Observable<UserRes>

}
