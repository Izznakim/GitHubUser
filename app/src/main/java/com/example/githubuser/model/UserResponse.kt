package com.example.githubuser.model

import com.google.gson.annotations.SerializedName

data class UserResponse(

	@field:SerializedName("items")
	val users: List<User>
)

data class User(

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("login")
	val username: String
)
