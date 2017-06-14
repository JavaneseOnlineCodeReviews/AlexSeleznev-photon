package com.applications.whazzup.photomapp.data.network

import com.applications.whazzup.photomapp.data.network.req.AddAlbumReq
import com.applications.whazzup.photomapp.data.network.req.UserLogInReq
import com.applications.whazzup.photomapp.data.network.req.UserSigInReq
import com.applications.whazzup.photomapp.data.network.res.AddAlbumRes
import com.applications.whazzup.photomapp.data.network.res.PhotocardRes
import com.applications.whazzup.photomapp.data.network.res.user.UserAlbumRes
import com.applications.whazzup.photomapp.data.network.res.user.UserRes
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.*


interface RestService {

    @GET("photocard/list")
    fun getPhotoCard(@Query("limit") limit: Int, @Query("offset") offset: Int): Observable<List<PhotocardRes>>

    @POST("user/signUp")
    fun sigUpUser(@Body user : UserSigInReq): Observable<UserRes>


    @POST("user/signIn")
    fun logInUser(@Body user: UserLogInReq) : Observable<UserRes>

    @GET("user/{userId}")
    fun getUserById(@Path("userId") userId : String): Observable<UserRes>


    @POST("user/{userId}/album")
    fun createAlbum(@Path("userId") userId : String,@Header("Authorization") userToken : String, @Body album : AddAlbumReq) : Observable<UserAlbumRes>

    @DELETE("user/{userId}")
    fun deleteUser(@Path("userId") userId : String, @Header("Authorization") userToken : String) : Observable<Response<Void>>

}
