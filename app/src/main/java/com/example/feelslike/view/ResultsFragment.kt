package com.example.feelslike.view

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Telephony
import android.telephony.SmsManager
import android.text.InputType
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.feelslike.MapServiceAware
import com.example.feelslike.R
import com.example.feelslike.databinding.FragmentResultsBinding
import com.example.feelslike.model.entity.CalculationsEntity
import com.example.feelslike.model.weather_service.WeatherApiService
import com.example.feelslike.utilities.FeelsLikeRepository
import com.example.feelslike.utilities.MapsService
import com.example.feelslike.view_model.ResultsViewModel
import com.example.feelslike.view_model.ResultsViewModelFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.net.PlacesClient
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ResultsFragment : Fragment(), OnMapReadyCallback, MapServiceAware
{
    private lateinit var binding : FragmentResultsBinding
    private lateinit var selectedPlace : CalculationsEntity
    private lateinit var dataRepo : FeelsLikeRepository
    private lateinit var map : GoogleMap
    private lateinit var mapsService : MapsService
    private lateinit var placesClient : PlacesClient
    private lateinit var viewModel : ResultsViewModel
    // A default location (Sydney, Australia) to use when location permission is not granted
    // to display upon first opening the app
    private val defaultLocation = LatLng(-33.8523341, 151.2106085)
    private var TAG = ResultsFragment::class.java.simpleName
    private var mapReady = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        /**
         * View bindings and model/factory setup
         */
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_results, container, false)

        val application = requireNotNull(this.activity).application

        val viewModelFactory = ResultsViewModelFactory(application)

        viewModel = ViewModelProvider(this, viewModelFactory).get(ResultsViewModel::class.java)

        binding.viewModel = viewModel

        binding.lifecycleOwner = this

        val searchAgainButton = binding.buttonsLocationCalculateResults.buttonCalculate

        searchAgainButton.text = getString(R.string.button_search_again)

        searchAgainButton.setOnClickListener {
            viewModel.onSearchAgainClicked()
        }

        viewModel.navigateToLandingPage.observe(viewLifecycleOwner, {
            if (it == true) {
                this.findNavController().navigate(
                    ResultsFragmentDirections.actionResultsFragmentToLandingPage()
                )
                viewModel.onNavigated()
            }
        })

        setupMap()
        updateMap(map)

        mapsService.setupLocationClient(activity)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val apiService = WeatherApiService()

        GlobalScope.launch(Dispatchers.Main) {
            val currentWeatherResponse = apiService.searchWeatherByPlaceName("Athens", R.string.WEATHER_API_KEY.toString()).await()
            Log.i(TAG, "Weather response ${currentWeatherResponse.weather}")
        }
    }

    /**
     * Text message - opens default SMS application.
     */

    private fun openSMS()
    {
//        val sendIntent = Intent(Intent.ACTION_VIEW)
//        sendIntent.data = Uri.parse("sms:"+issue.number)
//        ActivityCompat.startActivity(requireContext(), sendIntent, null)
    }
    /**
     * Maps
     */

    private fun updateMap(map: GoogleMap) {
        if (MapsService.mapReady && selectedPlace != null)
        {
            dataRepo.createCalculationsInfo().longitude = selectedPlace.longitude
            dataRepo.createCalculationsInfo().latitude = selectedPlace.latitude
            dataRepo.createCalculationsInfo().calculations_id = selectedPlace.calculations_id

            val marker = LatLng(selectedPlace.latitude, selectedPlace.longitude)
            map.addMarker(
                MarkerOptions()
                .position(marker))
        }
    }

    private fun setupMap()
    {
        (childFragmentManager.findFragmentById(R.id.map_results) as SupportMapFragment?)!!.getMapAsync(this)
        Log.i(TAG, "Map ready.")

        val mapFragment = childFragmentManager.findFragmentById(
            R.id.map_results) as SupportMapFragment?

        mapFragment?.getMapAsync{
                googleMap -> map = googleMap
            mapReady = true
            populateMap(googleMap)
        }

        this.mapsService = MapsService.getInstance()

        placesClient = mapsService.setupPlacesClient(activity)
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

    private fun showCreateFavoriteDialog()
    {
        val dialogPrompt = getString(R.string.text_name_favorite)
        val saveButtonTitle = getString(R.string.text_save)

        val builder = AlertDialog.Builder(context)
        val favoritesNameEditText = EditText(context)
        favoritesNameEditText.inputType = InputType.TYPE_CLASS_TEXT

        builder.setTitle(dialogPrompt)
        builder.setView(favoritesNameEditText)

        builder.setPositiveButton(saveButtonTitle)
        {
            dialog, _ -> dialog.dismiss()
        }
        builder.create().show()
        Log.i(TAG, "Favorite created")
    }

    override fun setMapService(mapsService: MapsService) {
        this.mapsService = mapsService
    }
}