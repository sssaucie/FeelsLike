package com.example.feelslike.model.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.OnConflictStrategy.REPLACE
import com.example.feelslike.model.entity.FavoritesEntity

@Dao
interface FavoritesDao
{
    @Query("SELECT * from favorite_list")
    fun loadFavorites() : LiveData<List<FavoritesEntity>>

    @Query("SELECT * FROM favorite_list WHERE favorites_entity_id = :favoriteId")
    fun loadFavorite(favoriteId : Long) : FavoritesEntity

    @Query("SELECT * FROM favorite_list WHERE favorites_entity_id = :favoriteId")
    fun loadLiveFavorite(favoriteId : Long) : LiveData<FavoritesEntity>

    @Insert(onConflict = IGNORE)
    fun insertFavorite(favorite : FavoritesEntity) : Long

    @Update(onConflict = REPLACE)
    fun updateFavorite(favorite : FavoritesEntity)

    @Delete
    fun deleteFavorite(favorite : FavoritesEntity)
}