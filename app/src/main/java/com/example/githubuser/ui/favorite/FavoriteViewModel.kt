package com.example.githubuser.ui.favorite

import androidx.lifecycle.ViewModel
import com.example.githubuser.data.FavoriteRepository

class FavoriteViewModel (private val noteRepository: FavoriteRepository) : ViewModel() {

    fun getUserFavorite() = noteRepository.getAllUser()

}