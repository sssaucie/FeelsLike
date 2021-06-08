package com.example.feelslike

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.feelslike.model.database.FeelsLikeDatabase
import com.example.feelslike.model.entity.Dummy
import com.example.feelslike.utilities.ADAPTER_POSITION
import com.example.feelslike.utilities.FIRST_RUN_KEY
import com.example.feelslike.utilities.SHARED_PREFS_KEY
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), OnMapReadyCallback
{
    private lateinit var linearLayoutManager : LinearLayoutManager
    private lateinit var map : GoogleMap
    private var position : Int = 0

    override fun onMapReady(googleMap : GoogleMap)
    {
        map = googleMap
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        linearLayoutManager = LinearLayoutManager(this, orientation(), false)

        (supportFragmentManager.findFragmentById(
            R.id.mapsFragment
        ) as SupportMapFragment?)?.getMapAsync(this)

//        val navController = findNavController(R.id.nav_host_fragment)
//        val navInflater = navController.navInflater
//        val navGraph = navInflater.inflate(R.navigation.nav_graph)
//
//        val destination : Int = if (intent.getBooleanExtra(
//                IS_NOT_FIRST_RUN,
//                false)
//        ) openFragment(findViewById(R.id.nav_graph), true)
//        else openFragment(findViewById(R.id.initialUserInputFragment), false)
//
//        navGraph.startDestination = destination
//        navController.graph = navGraph

        if(savedInstanceState != null)
        {
            position = savedInstanceState.getInt(ADAPTER_POSITION)
        }

        checkFirstRun()
    }

//    companion object
//    {
//        private const val IS_NOT_FIRST_RUN = "isNotFirstRunApp"
//
//        fun openFragment(context : Context, isNotFirstRun : Boolean): Int {
//            context.startActivity(Intent(context, MainActivity::class.java).apply {
//                putExtra(IS_NOT_FIRST_RUN, isNotFirstRun)
//            })
//            return openFragment(context, isNotFirstRun)
//        }
//    }

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
        else
        {

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
}