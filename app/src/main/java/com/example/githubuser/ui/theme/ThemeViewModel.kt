package com.example.githubuser.ui.theme

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class ThemeViewModel (private val pref: SettingPreference) : ViewModel(){

    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }

}