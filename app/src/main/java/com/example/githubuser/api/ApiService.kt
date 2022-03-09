package com.example.githubuser.api

import com.example.githubuser.BuildConfig
import com.example.githubuser.model.User
import com.example.githubuser.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface ApiService {
    @Headers("Authorization: ${BuildConfig.GITHUB_TOKEN}")
    @GET("users")
    fun getListUser():Call<List<User>>

    @GET("search/users?q={username}")
    fun getSearchUser(
        @Path("login") username:String
    ):Call<UserResponse>
}