package com.example.githubuser.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.R
import com.example.githubuser.api.ApiConfig
import com.example.githubuser.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {

    private val _listUser=MutableLiveData<List<User>>()
    val listUser:LiveData<List<User>> =_listUser

    private val _isLoading= MutableLiveData<Boolean>()
    val isLoading:LiveData<Boolean> =_isLoading

    init {
        getListUser()
    }

    private fun getListUser() {
        _isLoading.value=true
        val client= ApiConfig.getApiService().getListUser()
        client.enqueue(object : Callback<List<User>> {
            override fun onResponse(
                call: Call<List<User>>,
                response: Response<List<User>>
            ) {
                _isLoading.value=false
                if (response.isSuccessful){
                    _listUser.value=response.body()
                }else{
                    Log.e(TAG, "onFailure: ${response.message()}", )
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                _isLoading.value=false
                Log.e(TAG, "onFailure: ${t.message}", )
            }

        })
    }

    companion object{
        private const val TAG = "MainViewModel"
    }
}