package com.example.githubuser.di

import android.content.Context
import com.example.githubuser.data.FavoriteRespository
import com.example.githubuser.data.local.room.FavoriteDatabase
import com.example.githubuser.data.remote.retrofit.ApiConfig
import com.example.githubuser.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): FavoriteRespository {
        val apiService = ApiConfig.getApiService()
        val database = FavoriteDatabase.getInstance(context)
        val dao = database.favoriteDao()
        val appExecutors = AppExecutors()
        return FavoriteRespository.getInstance(apiService, dao, appExecutors)
    }
}