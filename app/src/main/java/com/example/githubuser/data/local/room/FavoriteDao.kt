package com.example.githubuser.data.local.room

import androidx.room.*
import com.example.githubuser.data.local.entity.FavoriteEntity

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserToFavorite(favorite: List<FavoriteEntity>)

    @Update
    fun updateFavoriteUser(favorite: FavoriteEntity)

    @Query("SELECT EXISTS(SELECT * FROM favorites WHERE username LIKE '%' || :username || '%')")
    suspend fun checkExistOrNot(username:String):Boolean

    @Delete
    fun deleteUserFromFavorite(favorite: FavoriteEntity)
}