package com.example.githubuser.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class UserResponse(

    @field:SerializedName("items")
    val users: List<User>
)

@Parcelize
data class User(

    @field:SerializedName("avatar_url")
    val avatarUrl: String,

    @field:SerializedName("login")
    val username: String
) : Parcelable
