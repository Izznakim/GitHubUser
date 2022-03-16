package com.example.githubuser.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.api.ApiConfig
import com.example.githubuser.model.DetailUserResponse
import com.example.githubuser.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel: ViewModel() {
    private val _detailUser=MutableLiveData<DetailUserResponse>()
    val detailUser:LiveData<DetailUserResponse> =_detailUser

    private val _listFoll=MutableLiveData<List<User>>()
    val listFoll:LiveData<List<User>> =_listFoll

    private val _isLoading=MutableLiveData<Boolean>()
    val isLoading:LiveData<Boolean> =_isLoading

    fun getDetailUser(username:String){
        _isLoading.value=true
        val client=ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value=false
                if (response.isSuccessful){
                    _detailUser.value=response.body()
                }else{
                    Log.e(TAG, "onFailure: ${response.message()}", )
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value=false
                Log.e(TAG, "onFailure: ${t.message}", )
            }

        })
    }

    fun getFollower(username:String){
        _isLoading.value=true
        val client= ApiConfig.getApiService().getFollowers(username)
        client.enqueue(object : Callback<List<User>> {
            override fun onResponse(
                call: Call<List<User>>,
                response: Response<List<User>>
            ) {
                _isLoading.value=false
                if (response.isSuccessful){
                    _listFoll.value=response.body()
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

    fun getFollowing(username:String){
        _isLoading.value=true
        val client= ApiConfig.getApiService().getFollowing(username)
        client.enqueue(object : Callback<List<User>> {
            override fun onResponse(
                call: Call<List<User>>,
                response: Response<List<User>>
            ) {
                _isLoading.value=false
                if (response.isSuccessful){
                    _listFoll.value=response.body()
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

    companion object {
        private const val TAG = "DetailViewModel"
    }
}