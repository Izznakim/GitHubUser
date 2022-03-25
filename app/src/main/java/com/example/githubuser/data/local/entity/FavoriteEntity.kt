package com.example.githubuser.data.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "favorites")
class FavoriteEntity(
    @field:PrimaryKey
    val username: String,

    val avatarUrl: String
) : Parcelable