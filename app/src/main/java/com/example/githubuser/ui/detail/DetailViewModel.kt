package com.example.githubuser.ui.detail

import android.util.Log
import androidx.lifecycle.*
import com.example.githubuser.data.FavoriteRepository
import com.example.githubuser.data.FavoriteEntity
import com.example.githubuser.model.UserDetailResponse
import com.example.githubuser.remote.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailViewModel (private val favoriteRepository: FavoriteRepository) : ViewModel(){
    private val _detailUser = MutableLiveData<UserDetailResponse>()
    val detailUser: LiveData<UserDetailResponse> = _detailUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun Insert(user: FavoriteEntity){
        favoriteRepository.insert(user)
    }

    fun delete(user: FavoriteEntity){
        favoriteRepository.delete(user)
    }

    fun FindUserById(usrname : String) = favoriteRepository.getFavoriteUserByUsername(usrname)

    fun findData(username : String){
        _isLoading.value = true
        val client =ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<UserDetailResponse> {
            override fun onResponse(
                call: Call<UserDetailResponse>,
                response: Response<UserDetailResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    val responseBody = response.body()
                    if (responseBody !=null){
                        _detailUser.value = (responseBody)
                    }
                }else{
                    Log.e(DetailActivity.TAG,"onFaliure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
                _isLoading.value =false
                Log.e(DetailActivity.TAG,"onFaliure : ${t.message}")
            }
        })

    }

}