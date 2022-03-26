package com.example.githubuser.di

import android.content.Context
import com.example.githubuser.data.FavoriteRepository
import com.example.githubuser.data.local.room.FavoriteDatabase

object Injection {
    fun provideRepository(context: Context): FavoriteRepository {
        val database = FavoriteDatabase.getInstance(context)
        val dao = database.favoriteDao()
        return FavoriteRepository.getInstance(dao)
    }
}