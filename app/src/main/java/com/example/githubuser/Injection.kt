package com.example.githubuser

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.githubuser.data.FavoriteDatabase
import com.example.githubuser.data.FavoriteRepository
import com.example.githubuser.remote.ApiConfig
import com.example.githubuser.ui.theme.SettingPreference

private val Context.dataStore : DataStore<Preferences> by preferencesDataStore(name = "setting")
object Injection {
    fun provideRepository(context: Context): FavoriteRepository {
        val apiService = ApiConfig.getApiService()
        val database = FavoriteDatabase.getDatabase(context)
        val dao = database.favDao()
        return FavoriteRepository(apiService,dao)
    }
    fun preference(context: Context) : SettingPreference{
        return SettingPreference.getInstance(context.dataStore)
    }

}