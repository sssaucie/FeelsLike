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
import com.example.feelslike.model.dao.UserDao
import com.example.feelslike.model.entity.Dummy
import com.example.feelslike.model.entity.UserEntity
import com.example.feelslike.utilities.USER_DATABASE_NAME

/**
 * The Room database for this app
 */

@Database(entities = [UserEntity::class, Dummy::class],
    version = 1,
    exportSchema = false)
abstract class UserDatabase : RoomDatabase()
{
    /**
     * Connects the database to the DAO.
     */

    abstract fun userDao() : UserDao
    abstract fun dummyDao() : DummyDao
    abstract fun calculationsDao() : CalculationsDao

    /**
     * Defining a companion object allows us to add functions on the UserDatabase
     * class.
     *
     * For example, clients can call 'UserDatabase.getInstance(context)' to
     * instantiate a new UserDatabase.
     */
    companion object
    {
        /**
         * Singleton prevents multiple instances of database opening at the same time.
         */

        @Volatile
        private var instance : UserDatabase? = null

        fun getInstance(context : Context) : UserDatabase
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

        private fun buildDatabase(context : Context) : UserDatabase
        {
            return Room.databaseBuilder(context, UserDatabase::class.java, USER_DATABASE_NAME)
                .addCallback(
                    object : RoomDatabase.Callback()
                    {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            val request = OneTimeWorkRequestBuilder<SeedUserDatabaseWorker>().build()
                            WorkManager.getInstance(context).enqueue(request)
                        }
                    }
                )
                .build()
        }
    }
}