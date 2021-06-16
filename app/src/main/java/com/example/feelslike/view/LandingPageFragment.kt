package com.example.feelslike.view

import android.app.SearchManager
import android.content.Context.*
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.feelslike.BuildConfig
import com.example.feelslike.MainActivity
import com.example.feelslike.R
import com.example.feelslike.databinding.FragmentLandingPageBinding
import com.example.feelslike.model.weather_service.WeatherInterface
import com.example.feelslike.utilities.WeatherRepo
import com.example.feelslike.view_model.SharedViewModel
import com.example.feelslike.view_model.SharedViewModelFactory
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LandingPageFragment : Fragment(), OnMapReadyCallback
{
    private lateinit var binding : FragmentLandingPageBinding
    private lateinit var map : GoogleMap
    private var TAG = LandingPageFragment::class.java.simpleName
    private var intent = Intent()

    @DelicateCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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

        val mapFragment = childFragmentManager.findFragmentById(
            R.id.landing_page_map) as SupportMapFragment?

        mapFragment?.getMapAsync(this)

//        (activity as AppCompatActivity).setSupportActionBar(binding.widgetSearchCustomView)

        binding.viewModel = sharedViewModel

        val searchBar = childFragmentManager.findFragmentById(
            R.id.widget_search_custom_view_landing_page) as AutocompleteSupportFragment?

        searchBar?.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME))

        searchBar?.setOnPlaceSelectedListener(object : PlaceSelectionListener
        {
            override fun onPlaceSelected(place : Place)
            {
                TODO("Not yet implemented")
                Log.i(TAG, "Place: ${place.name}, ${place.id}")
            }

            override fun onError(status : Status) {
                TODO("Not yet implemented")
                Log.i(TAG, "An error has occurred: $status")
            }
        })

//        binding.widgetLocationCalculateButtons = sharedViewModel

//        binding.headerLandingPageMenuProfile.viewModel = sharedViewModel

        binding.lifecycleOwner = this

//        binding.widgetSearchCustomView.setupWithNavController(navController)

//        sharedViewModel.navigateToResultsFragment.observe(viewLifecycleOwner, { selectedPlace ->
//        selectedPlace?.let {
//                this.findNavController().navigate(
//                    LandingPageFragmentDirections.actionLandingPageToResultsFragment(selectedPlace))
//                sharedViewModel.onNavigated()
//            }
//        })

//        sharedViewModel.navigateToResultsFragment2.observe(viewLifecycleOwner, {
//            if (it == true) {
//                this.findNavController().navigate(
//                    LandingPageFragmentDirections.actionLandingPageToResultsFragment(null))
//                sharedViewModel.onNavigated()
//            }
//        })

        sharedViewModel?.navigateToPlannedLocationFragment?.observe(viewLifecycleOwner, {
            if (it == true) {
                this.findNavController().navigate(
                    LandingPageFragmentDirections.actionLandingPageToPlannedLocationWidget())
                sharedViewModel.onNavigated()
            }
        })

        // Observe Intent changes and handle accordingly
        sharedViewModel?.intent?.get?.observe(viewLifecycleOwner, {
            handleIntent(intent)
        })

        setHasOptionsMenu(true)

        return binding.root
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        activity?.let {
//            val intent = Intent (it, MapsFragment::class.java)
//            it.startActivity(intent)
//        }
//    }

    /**
     * Map
     */

    @DelicateCoroutinesApi
    override fun onMapReady(googleMap : GoogleMap)
    {
        map = googleMap
        Log.i(TAG, "onMapReady accessed")
    }

    /**
     * Search bar
     */

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater)
    {
        inflater.inflate(R.menu.menu_search, menu)

        val searchItem = menu.findItem(R.id.search_item)
        val searchView = searchItem.actionView as SearchView?

        searchView?.findViewById<AutoCompleteTextView>(R.id.search_src_text)?.threshold = 1

        requireActivity().getSystemService(SEARCH_SERVICE) as SearchManager

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
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

//
//    @DelicateCoroutinesApi
//    private fun onNewIntent(intent: Intent)
//    {
//        handleIntent(intent)
//    }

}