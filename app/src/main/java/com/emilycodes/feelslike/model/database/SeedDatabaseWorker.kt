package com.emilycodes.feelslike.model.database

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.emilycodes.feelslike.model.entity.UserEntity
import com.emilycodes.feelslike.utilities.STANDARD_USER_DATA_FILENAME
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import kotlinx.coroutines.coroutineScope

class SeedDatabaseWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams)
{
    override suspend fun doWork(): Result = coroutineScope {
        val database = FeelsLikeDatabase.getInstance(applicationContext)
        try
        {
            applicationContext.assets.open(STANDARD_USER_DATA_FILENAME).use { inputStream ->
                JsonReader(inputStream.reader()).use { jsonReader ->
                    val userInput = object : TypeToken<List<UserEntity>>() {}.type
                    val userList : UserEntity =
                        Gson().fromJson(jsonReader,userInput)

                    database.userDao().insertAllUserInfo(userList)
                    Result.success()
                }
            }
        }
        catch (ex: Exception)
        {
            Log.e(TAG, "Error seeding database", ex)
            Result.failure()
        }
    }

    companion object
    {
        private const val TAG = "DatabaseWorker"
    }
}