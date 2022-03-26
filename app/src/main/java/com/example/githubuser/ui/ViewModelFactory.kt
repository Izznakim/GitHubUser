package com.example.githubuser.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubuser.data.FavoriteRepository
import com.example.githubuser.di.Injection
import com.example.githubuser.ui.detail.DetailViewModel
import com.example.githubuser.ui.favorite.FavoriteViewModel
import com.example.githubuser.ui.settings.SettingsViewModel
import com.example.githubuser.utils.SettingPreferences

class ViewModelFactory(
    private val favoriteRepository: FavoriteRepository? = null,
    private val pref: SettingPreferences? = null
) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> return favoriteRepository?.let {
                DetailViewModel(
                    it
                )
            } as T
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> return favoriteRepository?.let {
                FavoriteViewModel(
                    it
                )
            } as T
            modelClass.isAssignableFrom(SettingsViewModel::class.java) -> return pref?.let {
                SettingsViewModel(
                    it
                )
            } as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory = instance ?: synchronized(this) {
            instance ?: ViewModelFactory(Injection.provideRepository(context), null)
        }.also { instance = it }
    }
}