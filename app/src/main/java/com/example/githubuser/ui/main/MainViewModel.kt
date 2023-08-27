package com.example.githubuser.ui.main

import android.util.Log
import androidx.lifecycle.*
import com.example.githubuser.ItemsItem
import com.example.githubuser.UserResponse
import com.example.githubuser.remote.ApiConfig
import com.example.githubuser.ui.theme.SettingPreference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainViewModel (private val preferences: SettingPreference) : ViewModel(){
    private val _listuser = MutableLiveData<List<ItemsItem>>()
    val listUser: LiveData<List<ItemsItem>> = _listuser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    val searchUser = MutableLiveData<String>()

    fun getTheme() = preferences.getThemeSetting().asLiveData()


    companion object {
        private const val TAG = "MainActivity"
        private const val QUERY = "arif"
    }

    init {
        getUser(QUERY)
    }

    fun getUser(keyword: String) {
        _isLoading.value = true
        val api = ApiConfig.getApiService().getSearchUser(keyword)
        api.enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listuser.value = responseBody.items
                    }

                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    class Factory(private val preferences: SettingPreference) :
        ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            MainViewModel(preferences) as T
    }
}