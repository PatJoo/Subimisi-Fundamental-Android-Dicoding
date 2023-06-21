package com.dicoding.mygithub.api

import com.dicoding.mygithub.model.User
import com.dicoding.mygithub.model.UserResponse
import com.dicoding.mygithub.utils.DetailUserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiClient {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getSearchUsers(
        @Query("q") query: String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getUserDetail(
        @Path("username") username: String?
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(
        @Path("username") username: String
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<User>>
}