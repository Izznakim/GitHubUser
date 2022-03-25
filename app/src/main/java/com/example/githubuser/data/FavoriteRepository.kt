package com.example.githubuser.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.githubuser.data.local.entity.FavoriteEntity
import com.example.githubuser.data.local.room.FavoriteDao
import com.example.githubuser.data.remote.response.User
import com.example.githubuser.data.remote.retrofit.ApiService
import com.example.githubuser.utils.AppExecutors

class FavoriteRepository private constructor(
    private val apiService: ApiService,
    private val favoriteDao: FavoriteDao,
    private val appExecutors: AppExecutors
) {
    private val result = MediatorLiveData<Result<List<FavoriteEntity>>>()

    fun setUserToFavorite(user: User? = null, favoriteEntity: FavoriteEntity? = null) {
        appExecutors.diskIO.execute {
            val username = user?.username ?: favoriteEntity?.username
            val avatarUrl = user?.avatarUrl ?: favoriteEntity?.avatarUrl
            if (username != null && avatarUrl != null) {
                val favorite = FavoriteEntity(username, avatarUrl)
                val favoriteList = ArrayList<FavoriteEntity>()
                favoriteList.add(favorite)
                favoriteDao.insertUserToFavorite(favoriteList)
            }
        }
    }

    fun getListFavorite(): LiveData<Result<List<FavoriteEntity>>> {
        result.value = Result.Loading
        val favorite = favoriteDao.getListFavorite()
        result.addSource(favorite) { newData: List<FavoriteEntity> ->
            result.value = Result.Success(newData)
        }
        return result
    }

    suspend fun checkExistOrNot(username: String): Boolean {
        return favoriteDao.checkExistOrNot(username)
    }

    fun deleteUserFromFavorite(username: String) {
        appExecutors.diskIO.execute {
            favoriteDao.deleteUserFromFavorite(username)
        }
    }

    companion object {
        @Volatile
        private var instance: FavoriteRepository? = null
        fun getInstance(
            apiService: ApiService,
            favoriteDao: FavoriteDao,
            appExecutors: AppExecutors
        ): FavoriteRepository =
            instance ?: synchronized(this) {
                instance ?: FavoriteRepository(apiService, favoriteDao, appExecutors)
            }.also { instance = it }
    }
}