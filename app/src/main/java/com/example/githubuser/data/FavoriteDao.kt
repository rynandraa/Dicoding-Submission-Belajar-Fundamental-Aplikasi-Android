package com.example.githubuser.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(note: FavoriteEntity)
    @Update
    fun update(note: FavoriteEntity)
    @Delete
    fun delete(note: FavoriteEntity)

    @Query("SELECT * from FavoriteEntity ORDER BY username ASC")
    fun getAllUser(): LiveData<List<FavoriteEntity>>

    @Query("SELECT * FROM FavoriteEntity WHERE username = :username")
    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteEntity>
}