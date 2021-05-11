package com.example.feelslike

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.feelslike.model.database.FeelsLikeDatabase
import com.example.feelslike.model.entity.Dummy
import com.example.feelslike.utilities.FIRST_RUN_KEY
import com.example.feelslike.utilities.SHARED_PREFS_KEY
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity()
{
    private lateinit var linearLayoutManager: LinearLayoutManager
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        linearLayoutManager = LinearLayoutManager(this)

        checkFirstRun()
    }

    fun checkFirstRun()
    {
        val prefs = getSharedPreferences(SHARED_PREFS_KEY, Context.MODE_PRIVATE)
        val firstRun = prefs.getBoolean(FIRST_RUN_KEY, true)

        if (firstRun)
        {
            prefs.edit().putBoolean(FIRST_RUN_KEY, false)

            /**
             * We use the lifecycleScope.launch to tell the local Android OS to initialize
             * the database in the background, saving precious seconds of the user's time.
             * This initialization only needs to happen the first time the app is launched,
             * so it is included in the firstRun 'if' statement. Database build is then
             * complete and the phone OS has it stored locally.
             */

            lifecycleScope.launch {
                forceDatabaseInit()
            }
        }
    }

    /**
     * In this function, we're calling the getInstance function from [FeelsLikeDatabase].
     * The context is itself, so we tell it that this is the context.
     */
    private suspend fun forceDatabaseInit()
    {
        val db = FeelsLikeDatabase.getInstance(this)

        val dummy = Dummy("dummy")

        db.dummyDao().insert(dummy)
    }
}