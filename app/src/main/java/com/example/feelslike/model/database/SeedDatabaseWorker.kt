package com.example.feelslike.model.database

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.feelslike.utilities.Constants
import kotlinx.coroutines.coroutineScope
import java.lang.Exception

class SeedDatabaseWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams)
{
    override suspend fun doWork(): Result = coroutineScope {
        val database = FeelsLikeDatabase.getInstance(applicationContext)
        try
        {
//            applicationContext.assets.open(FEELS_LIKE_DATA_FILENAME).use { inputStream ->
//                JsonReader(inputStream.reader()).use { jasonReader ->
//                    val userInput = object :TypeToken<List<FeelsLikeEntity>>() {}.type
//                    val feelsLikeList : List<FeelsLikeEntity> =Gson().fromJson(jasonReader,userInput)
//
//                    database.feelsLikeDao().insertAll(feelsLikeList)
//                    Result.success()
//                }
//            }
            database.feelsLikeDao().insert(Constants.DUMMY_INPUT)
            Result.success()
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