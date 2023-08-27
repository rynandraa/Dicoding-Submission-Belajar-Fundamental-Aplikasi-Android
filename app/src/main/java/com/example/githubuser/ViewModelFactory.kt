package com.example.githubuser

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubuser.data.FavoriteRepository
import com.example.githubuser.ui.detail.DetailViewModel
import com.example.githubuser.ui.favorite.FavoriteViewModel
import com.example.githubuser.ui.main.MainViewModel
import com.example.githubuser.ui.theme.SettingPreference
import com.example.githubuser.ui.theme.ThemeViewModel

class ViewModelFactory private constructor(private val noteRepository: FavoriteRepository,private val pref: SettingPreference) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(noteRepository) as T
        }
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(pref) as T
        }
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(noteRepository) as T
        }
        if (modelClass.isAssignableFrom(ThemeViewModel::class.java)) {
            return ThemeViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }


    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(Injection.provideRepository(context),Injection.preference(context))
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}