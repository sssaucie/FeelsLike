package com.example.feelslike.view

import android.app.SearchManager
import android.content.Context.*
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.feelslike.BuildConfig
import com.example.feelslike.MapServiceAware
import com.example.feelslike.R
import com.example.feelslike.databinding.FragmentLandingPageBinding
import com.example.feelslike.model.weather_service.WeatherInterface
import com.example.feelslike.utilities.KEY_LOCATION
import com.example.feelslike.utilities.MapsService
import com.example.feelslike.utilities.WeatherRepo
import com.example.feelslike.view_model.SharedViewModel
import com.example.feelslike.view_model.SharedViewModelFactory
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LandingPageFragment : Fragment(), OnMapReadyCallback, MapServiceAware
{
    private lateinit var calculateButton: Button
    private lateinit var binding : FragmentLandingPageBinding
    private lateinit var map : GoogleMap
    private lateinit var mapsService : MapsService
    private lateinit var placesClient: PlacesClient
    private var TAG = LandingPageFragment::class.java.simpleName
    private var intent = Intent()
    private var mapReady = false
    private var lastKnownLocation : Location? = null
    // A default location (Sydney, Australia) to use when location permission is not granted
    // to display upon first opening the app
    private val defaultLocation = LatLng(-33.8523341, 151.2106085)

    private lateinit var lastSelectedPlace : Place

    override fun setMapService(mapsService: MapsService)
    {
        this.mapsService = mapsService
    }

    @DelicateCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
                /**
         * View bindings and model/factory setup
         */
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_landing_page, container, false)

        val application = requireNotNull(this.activity).application

        val viewModelFactory =
            binding.viewModel?.intent?.let { SharedViewModelFactory(application, it) }

        val sharedViewModel =
            viewModelFactory?.let {
                ViewModelProvider(
                    this, it
                ).get(SharedViewModel::class.java)
            }

        /**
         * Maps and Places initial setup
         */

        val mapFragment = childFragmentManager.findFragmentById(
            R.id.landing_page_map) as SupportMapFragment?

        mapFragment?.getMapAsync{
            googleMap -> map = googleMap
            mapReady = true
            populateMap(googleMap)
        }

        this.mapsService = MapsService.getInstance()

        placesClient = mapsService.setupPlacesClient(activity)
        setupMap()
        mapsService.setupLocationClient(activity)

        /**
         * Search bar view setup
         */

//        (activity as AppCompatActivity).setSupportActionBar(binding.widgetSearchCustomView)

        binding.viewModel = sharedViewModel

        val searchBar = childFragmentManager.findFragmentById(
            R.id.widget_search_custom_view_fragment) as AutocompleteSupportFragment?

        searchBar?.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME))

        searchBar?.setOnPlaceSelectedListener(object : PlaceSelectionListener
        {
            override fun onPlaceSelected(place : Place)
            {
                lastSelectedPlace = place
                calculateButton.isEnabled = true
                mapsService.displayPoi(activity, placesClient, place)
                Log.i(TAG, "Place: ${place.name}, ${place.id}")
                Log.i(TAG, "$place marker placed.")
            }

            override fun onError(status : Status) {
//                TODO("Not yet implemented")
                Log.i(TAG, "An error has occurred: $status")
            }
        })

        binding.lifecycleOwner = this

        /**
         * Fragment navigation observers
         */

//        binding.widgetSearchCustomView.setupWithNavController(navController)

        calculateButton = binding.widgetLocationCalculateButtons.buttonCalculate
        calculateButton.isEnabled = false

//        calculateButton.setOnClickListener {
//            if (lastSelectedPlace != null) {
//                sharedViewModel?.navigateToResultsFragment?.observe(
//                    viewLifecycleOwner,
//                    { selectedPlace ->
//                        selectedPlace?.let {
//                            this.findNavController().navigate(
//                                LandingPageFragmentDirections.actionLandingPageToResultsFragment()
//                            )
//                            sharedViewModel.onNavigated()
//                        }
//                    })
//            }
//        }

        sharedViewModel?.navigateToResultsFragment2?.observe(viewLifecycleOwner, {
            if (it == true) {
                this.findNavController().navigate(
                    LandingPageFragmentDirections.actionLandingPageToResultsFragment())
                sharedViewModel.onNavigated()
            }
        })

//        sharedViewModel?.navigateToPlannedLocationFragment?.observe(viewLifecycleOwner, {
//            if (it == true) {
//                this.findNavController().navigate(
//                    LandingPageFragmentDirections.actionLandingPageToPlannedLocationWidget())
//                sharedViewModel.onNavigated()
//            }
//        })

        // Observe Intent changes and handle accordingly
        sharedViewModel?.intent?.get?.observe(viewLifecycleOwner, {
            handleIntent(intent)
        })

        /**
         * View updates and finalization
         */

        fun checkSavedInstanceState()
        {
            if (savedInstanceState != null)
            {
                lastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION)
            }
            else
            {
                defaultLocation
            }
        }

        checkSavedInstanceState()

        setHasOptionsMenu(true)

        return binding.root
    }

    /**
     * Map
     */

    private fun setupMap()
    {
        (childFragmentManager.findFragmentById(R.id.landing_page_map
        ) as SupportMapFragment?)!!.getMapAsync(this)
        Log.i(TAG, "Map ready.")
    }
    @DelicateCoroutinesApi
    override fun onMapReady(googleMap : GoogleMap)
    {
        mapsService.onMapReady(activity, placesClient, googleMap)
        Log.i(TAG, "onMapReady accessed")
    }

    private fun populateMap(map : GoogleMap) {
        if (mapReady)
        {
//            dataRepo.createCalculationsInfo().longitude = selectedPlace.longitude
//            dataRepo.createCalculationsInfo().latitude = selectedPlace.latitude
//            dataRepo.createCalculationsInfo().calculations_id = selectedPlace.calculations_id

            val marker = defaultLocation
            map.addMarker(
                MarkerOptions()
                .position(marker))
            Log.i(TAG, "Map updated.")
        }
        else
        {
            Log.i(TAG, "LatLng not found")
        }
    }

    /**
     * Search bar functions
     */

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater)
    {
        inflater.inflate(R.menu.menu_search, menu)

        val searchItem = menu.findItem(R.id.search_item)
        val searchView = searchItem.actionView as SearchView?

        searchView?.findViewById<AutoCompleteTextView>(R.id.search_src_text)?.threshold = 1

        requireActivity().getSystemService(SEARCH_SERVICE) as SearchManager

        super.onCreateOptionsMenu(menu, inflater)
        Log.i(TAG, "onCreateOptionsMenu complete.")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
        Log.i(TAG, "$item selected.")
    }

    @DelicateCoroutinesApi
    override fun onStart() {
        super.onStart()

        if (requireActivity().intent.hasExtra(SearchManager.QUERY))
        {
            handleIntent(intent)
        }
    }

    @DelicateCoroutinesApi
    private fun performSearch(place : String)
    {
        val weatherInterface = WeatherInterface.instance
        val weatherRepo = WeatherRepo(weatherInterface)

        GlobalScope.launch {
            val results = weatherRepo.searchByPlace(place, BuildConfig.WEATHER_API_KEY)
            Log.i(TAG, "Results = ${results.body()}")
        }
    }

    @DelicateCoroutinesApi
    private fun handleIntent(intent : Intent)
    {
        if (Intent.ACTION_SEARCH == intent.action)
        {
            val query = intent.getStringExtra(SearchManager.QUERY) ?:
            return
            performSearch(query)
        }
    }
}