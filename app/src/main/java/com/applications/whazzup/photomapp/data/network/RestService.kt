package com.applications.whazzup.photomapp.data.network

import com.applications.whazzup.photomapp.data.network.req.*
import com.applications.whazzup.photomapp.data.network.res.AddAlbumRes
import com.applications.whazzup.photomapp.data.network.res.PhotocardRes
import com.applications.whazzup.photomapp.data.network.res.UserAvatarRes
import com.applications.whazzup.photomapp.data.network.res.user.UserAlbumRes
import com.applications.whazzup.photomapp.data.network.res.user.UserRes
import io.reactivex.Observable
import okhttp3.MultipartBody
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

    @PUT("user/{userId}")
    fun changeUserInfo(@Path("userId") userId : String, @Header("Authorization") userToken : String, @Body userInf : UserChangeInfoReq) : Observable<UserRes>

    @Multipart
    @POST("user/{userId}/image/upload")
    fun uploadPhoto(@Path("userId") userId : String, @Part file : MultipartBody.Part, @Header("Authorization") userToken : String) : Observable<UserAvatarRes>

    @GET("user/{userId}/album/{albumId}")
    fun getAlbumById(@Path("userId") userId : String, @Path("albumId") albumId : String) : Observable<UserAlbumRes>

    @DELETE("user/{userId}/photocard/{cardId}")
    fun deletePhotoCard(@Path("userId") userId : String, @Header("Authorization") userToken : String, @Path("cardId") cardId: String) : Observable<Response<Void>>

    @PUT("user/{userId}/album/{albumId}")
    fun changeAlbumInfo(@Path("userId") userId : String, @Header("Authorization") userToken : String, @Path("albumId") albumId : String, @Body albumInf : AlbumChangeInfoReq) : Observable<UserAlbumRes>

    @DELETE("user/{userId}/album/{albumId}")
    fun deleteAlbum(@Path("userId") userId : String, @Header("Authorization") userToken : String, @Path("albumId") albumId : String) : Observable<Response<Void>>

}
