package com.example.client.api.service

import com.example.csc_mvvm.data.dto.profile.DataProfileResponse
import com.example.csc_mvvm.data.dto.profile.ProfileResponse
import com.example.csc_mvvm.base.BaseResponse
import retrofit2.Call
import retrofit2.http.*

interface UserService {
    @GET("api/user/checkEmail.php")
    fun checkEmail(@Query("email") email: String): Call<BaseResponse<Any>>

//    @FormUrlEncoded
//    @POST("api/user/register.php")
//    fun register(
//            @Field("fullname") fullname: String,
//            @Field("phone") phone: String,
//            @Field("email") email: String,
//            @Field("password") password: String,
//    ): Observable<BaseResponse<DataProfileResponse>>
//
    @FormUrlEncoded
    @POST("api/user/login.php")
    fun login(
            @Field("email") email: String,
            @Field("password") password: String,
    ): Call<BaseResponse<DataProfileResponse>>

    @GET("api/user/getUserByEmail.php")
    fun getUserByEmail(@Query("email") email: String): Call<BaseResponse<ProfileResponse>>
//
//    @POST("api/user/updateInfo.php")
//    fun updateInfo(@Body profileRequest: ProfileRequest): Observable<BaseResponse<DataProfileResponse>>
//
//    @POST("api/user/updatePass.php")
//    fun updatePass(@Body profileRequest: ProfileRequest): Observable<BaseResponse<DataProfileResponse>>
//
//    @POST("api/user/updateAvatar.php")
//    fun updateAvatar(@Body profileRequest: ProfileRequest): Observable<BaseResponse<DataProfileResponse>>
//
//    @FormUrlEncoded
//    @POST("api/user/updateDeviceToken.php")
//    fun updateDeviceToken(
//            @Field("email") email: String,
//            @Field("device_token") device_token: String,
//    ): Observable<BaseResponse<DataProfileResponse>>
//
//    @GET("api/user/sendEmail.php")
//    fun sendEmail(
//            @Query("email") email: String,
//            @Query("phone") phone: String,
//            @Query("requestType") requestType: Constants.RequestType,
//    ): Observable<BaseResponse<DataProfileResponse>>
//
//    @FormUrlEncoded
//    @POST("api/user/verification.php")
//    fun verification(
//            @Field("email") email: String,
//            @Field("code") code: String,
//    ): Observable<BaseResponse<DataProfileResponse>>
//
//    @FormUrlEncoded
//    @POST("api/user/resetPassword.php")
//    fun resetPassword(
//            @Field("email") email: String,
//            @Field("password") password: String,
//    ): Observable<BaseResponse<DataProfileResponse>>
}