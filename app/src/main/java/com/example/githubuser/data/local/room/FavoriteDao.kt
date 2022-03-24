package com.example.githubuser.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import com.example.githubuser.data.local.entity.FavoriteEntity

@Dao
interface FavoriteDao {
    @Insert
    fun insertUserToFavorite(favorite: List<FavoriteEntity>)
}