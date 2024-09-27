package com.bangkit.fixmyrideapp.data.api

import com.bangkit.fixmyrideapp.data.response.DetailUserResponse
import com.bangkit.fixmyrideapp.data.response.LoginResponse
import com.bangkit.fixmyrideapp.data.response.NearbyRequest
import com.bangkit.fixmyrideapp.data.response.NearbyResponse
import com.bangkit.fixmyrideapp.data.response.NewsResponse
import com.bangkit.fixmyrideapp.data.response.RegisterResponse
import com.bangkit.fixmyrideapp.data.response.UpdateProfileResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {

    @FormUrlEncoded
    @POST("signup")
    fun postRegister(
        @Field("username") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
    ): Call<RegisterResponse>

    @FormUrlEncoded
    @POST("login")
    fun postLogin(
        @Field("email") email: String,
        @Field("password") password: String,
    ): Call<LoginResponse>

    @Multipart
    @POST("detailuser")
    fun updateDetailProfile(
        @Header("authorization") authorization: String,
        @Part image: MultipartBody.Part,
        @Part("email") email: RequestBody,
    ): Call<UpdateProfileResponse>

    @GET("detailuser/{email}")
    fun getDetailUser(
        @Header("authorization") authorization: String,
        @Path("email") email: String
    ): Call<DetailUserResponse>

    @GET("news")
    fun getNews(
    ): Call<NewsResponse>

    @POST("nearby")
    fun getNearby(
        @Body request: NearbyRequest
    ): Call<NearbyResponse>
}