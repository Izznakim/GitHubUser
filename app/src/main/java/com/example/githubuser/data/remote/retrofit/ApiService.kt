package com.example.githubuser.data.remote.retrofit

import com.example.githubuser.BuildConfig
import com.example.githubuser.data.remote.response.DetailUserResponse
import com.example.githubuser.data.remote.response.User
import com.example.githubuser.data.remote.response.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @Headers("Authorization: ${BuildConfig.GITHUB_TOKEN}")
    @GET("users")
    fun getListUser(): Call<List<User>>

    @GET("search/users")
    fun getSearchUser(
        @Query("q") username: String
    ): Call<UserResponse>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ): Call<List<User>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): Call<List<User>>
}