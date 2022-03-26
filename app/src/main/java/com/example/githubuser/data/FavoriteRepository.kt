package com.example.githubuser.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.example.githubuser.data.local.entity.FavoriteEntity
import com.example.githubuser.data.local.room.FavoriteDao
import com.example.githubuser.data.remote.response.User

class FavoriteRepository private constructor(
    private val favoriteDao: FavoriteDao
) {
    suspend fun setUserToFavorite(user: User? = null, favoriteEntity: FavoriteEntity? = null) {
        val username = user?.username ?: favoriteEntity?.username
        val avatarUrl = user?.avatarUrl ?: favoriteEntity?.avatarUrl
        if (username != null && avatarUrl != null) {
            val favorite = FavoriteEntity(username, avatarUrl)
            val favoriteList = ArrayList<FavoriteEntity>()
            favoriteList.add(favorite)
            favoriteDao.insertUserToFavorite(favoriteList)
        }
    }

    fun getListFavorite(): LiveData<Result<List<FavoriteEntity>>> = liveData {
        emit(Result.Loading)
        val favorite: LiveData<Result<List<FavoriteEntity>>> =
            favoriteDao.getListFavorite().map { Result.Success(it) }
        emitSource(favorite)
    }

    suspend fun checkExistOrNot(username: String): Boolean {
        return favoriteDao.checkExistOrNot(username)
    }

    suspend fun deleteUserFromFavorite(username: String) {
        favoriteDao.deleteUserFromFavorite(username)
    }

    companion object {
        @Volatile
        private var instance: FavoriteRepository? = null
        fun getInstance(
            favoriteDao: FavoriteDao
        ): FavoriteRepository =
            instance ?: synchronized(this) {
                instance ?: FavoriteRepository(favoriteDao)
            }.also { instance = it }
    }
}