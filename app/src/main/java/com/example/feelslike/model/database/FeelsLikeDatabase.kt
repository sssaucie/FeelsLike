package com.example.feelslike.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.feelslike.model.dao.CalculationsDao
import com.example.feelslike.model.dao.DummyDao
import com.example.feelslike.model.dao.FavoritesDao
import com.example.feelslike.model.dao.UserDao
import com.example.feelslike.model.entity.CalculationsEntity
import com.example.feelslike.model.entity.Dummy
import com.example.feelslike.model.entity.FavoritesEntity
import com.example.feelslike.model.entity.UserEntity
import com.example.feelslike.utilities.DATABASE_NAME

/**
 * The Room database for this app
 */

@Database(entities = [UserEntity::class, Dummy::class, CalculationsEntity::class, FavoritesEntity::class],
    version = 1,
    exportSchema = false)
abstract class FeelsLikeDatabase : RoomDatabase()
{
    /**
     * Connects the database to the DAO.
     */

    abstract fun userDao() : UserDao
    abstract fun dummyDao() : DummyDao
    abstract fun calculationsDao() : CalculationsDao
    abstract fun favoritesDao() : FavoritesDao

    /**
     * Defining a companion object allows us to add functions on the FeelsLikeDatabase
     * class.
     *
     * For example, clients can call 'FeelsLikeDatabase.getInstance(context)' to
     * instantiate a new FeelsLikeDatabase.
     */
    companion object
    {
        /**
         * Singleton prevents multiple instances of database opening at the same time.
         */

        @Volatile
        private var instance : FeelsLikeDatabase? = null

        fun getInstance(context : Context) : FeelsLikeDatabase
        {
            /**
             * If the instance is not null, then return it.
             * If it is, then create the database.
             */
            return instance ?: synchronized(this)
            {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        /**
         * Create and pre-populate the database.
         */

        private fun buildDatabase(context : Context) : FeelsLikeDatabase
        {
            return Room.databaseBuilder(
                context,
                FeelsLikeDatabase::class.java,
                DATABASE_NAME)
                .addCallback(
                    object : RoomDatabase.Callback()
                    {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            val request = OneTimeWorkRequestBuilder<SeedDatabaseWorker>().build()
                            WorkManager.getInstance(context).enqueue(request)
                        }
                    }
                )
                .build()
        }
    }
}