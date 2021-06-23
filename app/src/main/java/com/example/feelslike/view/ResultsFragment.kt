package com.example.feelslike.view

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.feelslike.MapServiceAware
import com.example.feelslike.R
import com.example.feelslike.databinding.FragmentResultsBinding
import com.example.feelslike.utilities.FeelsLikeRepository
import com.example.feelslike.utilities.MapsService
import com.example.feelslike.view_model.ResultsViewModel
import com.example.feelslike.view_model.ResultsViewModelFactory
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.PlacesClient
import kotlinx.coroutines.DelicateCoroutinesApi
import java.util.*

class ResultsFragment : Fragment(), OnMapReadyCallback, MapServiceAware
{
    private lateinit var selectedPlace : Place
    private lateinit var binding : FragmentResultsBinding
    private lateinit var map : GoogleMap
    private lateinit var mapsService : MapsService
    private lateinit var placesClient : PlacesClient
    // A default location (Sydney, Australia) to use when location permission is not granted
    // to display upon first opening the app
    private val defaultLocation = LatLng(-33.8523341, 151.2106085)
    private var TAG = ResultsFragment::class.java.simpleName
    private var mapReady = false

    override fun setMapService(mapsService: MapsService)
    {
        this.mapsService = mapsService
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        selectedPlace = ResultsFragmentArgs.fromBundle(requireArguments()).selectedPlace

        /**
         * View bindings and model/factory setup
         */

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_results, container, false)

        val application = requireNotNull(this.activity).application

        val viewModelFactory = ResultsViewModelFactory(selectedPlace, application)

        val resultsViewModel = ViewModelProvider(this, viewModelFactory).get(ResultsViewModel::class.java)

        binding.viewModel = resultsViewModel

        binding.lifecycleOwner = this

        resultsViewModel.getWeatherResults(selectedPlace.name.toString())

        val searchAgainButton = binding.buttonsLocationCalculateResults.buttonCalculate

        val sendResultsButton = binding.buttonResultsText

        searchAgainButton.text = getString(R.string.button_search_again)

        searchAgainButton.setTextColor(resources.getColor(R.color.black))

        sendResultsButton.setOnClickListener {
            shareResultsDialog(resultsList().toString())
        }
        searchAgainButton.setOnClickListener {
            resultsViewModel.onSearchAgainClicked()
        }

        val dateText = binding.calculatedTextDate

        dateText.text = Calendar.getInstance().time.toString()

        resultsViewModel.navigateToLandingPage.observe(viewLifecycleOwner, {
            if (it == true) {
                this.findNavController().navigate(
                    ResultsFragmentDirections.actionResultsFragmentToLandingPage()
                )
                resultsViewModel.onNavigated()
            }
        })


//        sharedViewModel.getLastSelectedPosition().observe(viewLifecycleOwner,{
//            populateMap(map, it)
//        })

        setupMap()
        mapsService.setupLocationClient(activity)

        return binding.root
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
        if (MapsService.mapReady)
        {
            val marker = selectedPlace.latLng!!
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
            populateMap(googleMap, selectedPlace.latLng!!)
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(selectedPlace.latLng!!))
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

    private fun populateMap(map : GoogleMap, location: LatLng) {
        if (mapReady)
        {
//            dataRepo.createCalculationsInfo().longitude = selectedPlace.longitude
//            dataRepo.createCalculationsInfo().latitude = selectedPlace.latitude
//            dataRepo.createCalculationsInfo().calculations_id = selectedPlace.calculations_id

            map.addMarker(
                MarkerOptions()
                    .position(location))
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

    private fun resultsList() {
        binding.calculatedTextBarometric.toString()
        binding.calculatedTextHumidity.toString()
        binding.calculatedTextTemperature.toString()
    }

    private fun shareResultsDialog(text: String) {
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, text)
        context?.startActivity(Intent.createChooser(shareIntent, getString(R.string.text_send_to)))
    }
}