package com.example.githubuser.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubuser.data.FavoriteRepository
import com.example.githubuser.data.local.entity.FavoriteEntity
import com.example.githubuser.data.remote.response.DetailUserResponse
import com.example.githubuser.data.remote.response.User
import com.example.githubuser.data.remote.retrofit.ApiConfig
import com.example.githubuser.utils.Event
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(private val favoriteRepository: FavoriteRepository) : ViewModel() {
    private val _detailUser = MutableLiveData<DetailUserResponse>()
    val detailUser: LiveData<DetailUserResponse> = _detailUser

    private val _listFoll = MutableLiveData<List<User>>()
    val listFoll: LiveData<List<User>> = _listFoll

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    private val _snackbarText = MutableLiveData<Event<String>>()
    val snackbarText: LiveData<Event<String>> = _snackbarText

    fun getDetailUser(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _detailUser.value = response.body()
                } else {
                    _snackbarText.value = Event(response.message())
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                _snackbarText.value = Event(t.message.toString())
            }

        })
    }

    fun getFollower(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowers(username)
        client.enqueue(object : Callback<List<User>> {
            override fun onResponse(
                call: Call<List<User>>,
                response: Response<List<User>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listFoll.value = response.body()
                } else {
                    _snackbarText.value = Event(response.message())
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                _isLoading.value = false
                _snackbarText.value = Event(t.message.toString())
            }

        })
    }

    fun getFollowing(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(username)
        client.enqueue(object : Callback<List<User>> {
            override fun onResponse(
                call: Call<List<User>>,
                response: Response<List<User>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listFoll.value = response.body()
                } else {
                    _snackbarText.value = Event(response.message())
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                _isLoading.value = false
                _snackbarText.value = Event(t.message.toString())
            }

        })
    }

    fun setUserToFavorite(user: User? = null, favoriteEntity: FavoriteEntity? = null) {
        favoriteRepository.setUserToFavorite(user, favoriteEntity)
    }

    fun deleteUserFromFavorite(username: String) {
        favoriteRepository.deleteUserFromFavorite(username)
    }

    fun checkExistOrNot(username: String) {
        viewModelScope.launch {
            _isFavorite.value = favoriteRepository.checkExistOrNot(username)
        }
    }
}