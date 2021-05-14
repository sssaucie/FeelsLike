package com.example.feelslike.model.database

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.feelslike.model.entity.FeelsLikeEntity
import com.example.feelslike.utilities.FEELS_LIKE_DATA_FILENAME
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
            applicationContext.assets.open(FEELS_LIKE_DATA_FILENAME).use { inputStream ->
                JsonReader(inputStream.reader()).use { jsonReader ->
                    val userInput = object : TypeToken<List<FeelsLikeEntity>>() {}.type
                    val feelsLikeList : List<FeelsLikeEntity> =
                        Gson().fromJson(jsonReader,userInput)

                    database.feelsLikeDao().insertAll(feelsLikeList)
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
        private const val TAG = "FeelsLikeDatabaseWorker"
    }
}