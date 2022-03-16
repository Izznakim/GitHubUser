package com.example.githubuser.model

import com.google.gson.annotations.SerializedName

data class DetailUserResponse(

    @field:SerializedName("followers")
    val followers: Int,

    @field:SerializedName("following")
    val following: Int,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("company")
    val company: String,

    @field:SerializedName("location")
    val location: String,

    @field:SerializedName("public_repos")
    val repository: Int
)
