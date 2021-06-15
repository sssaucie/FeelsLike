package com.example.feelslike

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.feelslike.databinding.ActivityMainBinding
import com.example.feelslike.model.database.FeelsLikeDatabase
import com.example.feelslike.model.entity.Dummy
import com.example.feelslike.utilities.ADAPTER_POSITION
import com.example.feelslike.utilities.FIRST_RUN_KEY
import com.example.feelslike.utilities.SHARED_PREFS_KEY
import com.example.feelslike.view.MapsFragment
import com.example.feelslike.view_model.SharedViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity()
{
    private lateinit var binding : ActivityMainBinding
    private lateinit var linearLayoutManager : LinearLayoutManager
    private lateinit var viewModel : SharedViewModel
    private var position : Int = 0
    private val TAG = javaClass.simpleName

    @DelicateCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val toolbar = findViewById<Toolbar>(R.id.widget_search_custom_view)
        setSupportActionBar(toolbar)

        linearLayoutManager = LinearLayoutManager(this, orientation(), false)

        if(savedInstanceState != null)
        {
            position = savedInstanceState.getInt(ADAPTER_POSITION)
        }

        checkFirstRun()
    }

    private fun checkFirstRun()
    {
        val prefs = getSharedPreferences(SHARED_PREFS_KEY, Context.MODE_PRIVATE)
        val firstRun = prefs.getBoolean(FIRST_RUN_KEY, true)

        if (firstRun)
        {
            prefs.edit().putBoolean(FIRST_RUN_KEY, false).apply()

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

    private fun orientation() : Int = when(
        this.resources.configuration.orientation)
    {
        Configuration.ORIENTATION_PORTRAIT -> LinearLayout.HORIZONTAL
        else -> LinearLayout.VERTICAL
    }

    /**
     * Maps config
     */

    internal fun onOpenMap()
    {
        supportFragmentManager.beginTransaction()
            .replace(R.id.map_layout, MapsFragment())
            .commitNow()
    }

    @DelicateCoroutinesApi
    override fun onNewIntent(intent : Intent?)
    {
        super.onNewIntent(intent)
        if (intent != null) {
            viewModel.intent.setIntent(intent)
        }
    }
}