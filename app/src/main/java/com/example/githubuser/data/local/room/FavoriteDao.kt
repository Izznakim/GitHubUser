package com.example.githubuser.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.githubuser.data.local.entity.FavoriteEntity

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserToFavorite(favorite: List<FavoriteEntity>)

    @Query("SELECT * FROM favorites")
    fun getListFavorite(): LiveData<List<FavoriteEntity>>

    @Query("SELECT EXISTS(SELECT * FROM favorites WHERE username = :username)")
    suspend fun checkExistOrNot(username: String): Boolean

    @Query("DELETE FROM favorites WHERE username = :username")
    suspend fun deleteUserFromFavorite(username: String)
}