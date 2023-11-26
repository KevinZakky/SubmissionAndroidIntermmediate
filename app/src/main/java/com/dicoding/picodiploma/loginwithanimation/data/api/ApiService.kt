package com.dicoding.picodiploma.loginwithanimation.data.api

import com.dicoding.picodiploma.loginwithanimation.data.response.FileUploadResponse
import com.dicoding.picodiploma.loginwithanimation.data.response.ResponseDetail
import com.dicoding.picodiploma.loginwithanimation.data.response.ResponseLogin
import com.dicoding.picodiploma.loginwithanimation.data.response.ResponseRegister
import com.dicoding.picodiploma.loginwithanimation.data.response.ResponseStory
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): ResponseRegister

    @FormUrlEncoded
    @POST("login")
     fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ):Call<ResponseLogin>

    @GET("stories")
    suspend fun getStories(
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20,
        @Query("location") location : Int = 0,
    ): ResponseStory

    @GET("stories")
    suspend fun getStoriesFromLocation(
        @Query("location") location : Int = 1,
    ): ResponseStory

    @GET("stories/{id}")
    suspend fun getDetailStories(
        @Path("id") id:String,
    ):ResponseDetail

    @Multipart
    @POST("stories")
    fun uploadImage(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
    ):  Call<FileUploadResponse>
}