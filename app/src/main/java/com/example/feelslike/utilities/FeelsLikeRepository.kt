package com.example.feelslike.utilities

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.feelslike.model.dao.CalculationsDao
import com.example.feelslike.model.dao.FavoritesDao
import com.example.feelslike.model.dao.UserDao
import com.example.feelslike.model.database.FeelsLikeDatabase
import com.example.feelslike.model.entity.CalculationsEntity
import com.example.feelslike.model.entity.FavoritesEntity
import com.example.feelslike.model.entity.UserEntity

class FeelsLikeRepository(context : Context)
{
    private val database = FeelsLikeDatabase.getInstance(context)
    private val userDao : UserDao = database.userDao()
    private val calculationsDao : CalculationsDao = database.calculationsDao()
    private val favoritesDao : FavoritesDao = database.favoritesDao()

    fun addUser(user : UserEntity) : Long?
    {
        val newUserId = userDao.insertAllUserInfo(user)
        user.user_entity_id = newUserId
        return newUserId
    }

    fun createUser() : UserEntity
    {
        return UserEntity()
    }

    val allUserInfo : LiveData<List<UserEntity>>
        get() {
            return userDao.loadAllUserInfo()
        }

    fun addCalculation(calculation : CalculationsEntity) : Long?
    {
        val newCalculationId = calculationsDao.insertAllCalculationsInfo(calculation)
        calculation.calculations_entity_id = newCalculationId
        return newCalculationId
    }

    fun createCalculationsInfo() : CalculationsEntity
    {
        return CalculationsEntity()
    }

    val allCalculationsInfo : LiveData<List<CalculationsEntity>>
        get() {
            return calculationsDao.loadAllCalculationsInfo()
        }

    fun addFavorite(favorite : FavoritesEntity) : Long?
    {
        val newFavoriteId = favoritesDao.insertFavorite(favorite)
        favorite.favorites_entity_id = newFavoriteId
        return newFavoriteId
    }

    fun createFavorite() : FavoritesEntity
    {
        return FavoritesEntity()
    }

    val allFavorites : LiveData<List<FavoritesEntity>>
        get() {
            return favoritesDao.loadFavorites()
        }
}