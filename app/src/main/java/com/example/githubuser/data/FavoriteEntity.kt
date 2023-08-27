package com.example.githubuser.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class FavoriteEntity (
    @PrimaryKey(autoGenerate = false)
    var username: String = "",
    var avatarUrl: String,
)