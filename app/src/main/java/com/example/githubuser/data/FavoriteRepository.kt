package com.example.githubuser.data

import androidx.lifecycle.LiveData
import androidx.room.Entity
import com.example.githubuser.remote.GithubApiService
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Entity(tableName = "favorite_user")
class FavoriteRepository(apiService :GithubApiService, Dao: FavoriteDao){
    private val mFavDao :FavoriteDao =Dao
    private val executorService:ExecutorService = Executors.newSingleThreadExecutor()

    fun insert(favorite: FavoriteEntity) {
        executorService.execute { mFavDao.insert(favorite) }
    }
    fun delete(favorite: FavoriteEntity) {
        executorService.execute { mFavDao.delete(favorite) }
    }
    fun getAllUser() : LiveData<List<FavoriteEntity>> {
        return mFavDao.getAllUser()
    }
    fun getFavoriteUserByUsername(username : String): LiveData<FavoriteEntity> {
        return mFavDao.getFavoriteUserByUsername(username)
    }

}
