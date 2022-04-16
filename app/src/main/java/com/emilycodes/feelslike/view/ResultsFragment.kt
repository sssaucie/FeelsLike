package com.emilycodes.feelslike.view

import android.app.AlertDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.emilycodes.feelslike.R
import com.emilycodes.feelslike.databinding.FragmentResultsBinding
import com.emilycodes.feelslike.view_model.ResultsViewModel
import com.emilycodes.feelslike.view_model.ResultsViewModelFactory
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.model.Place
import kotlinx.coroutines.DelicateCoroutinesApi
import java.time.LocalDate

class ResultsFragment : Fragment(), OnMapReadyCallback
{
    private lateinit var selectedPlace : Place
    private lateinit var binding : FragmentResultsBinding
    private lateinit var map : GoogleMap
    private lateinit var resultsViewModel : ResultsViewModel
    // A default location (Sydney, Australia) to use when location permission is not granted
    // to display upon first opening the app
    private val defaultLocation = LatLng(-33.8523341, 151.2106085)
    private var TAG = ResultsFragment::class.java.simpleName
//    private var mapReady = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        selectedPlace = ResultsFragmentArgs.fromBundle(requireArguments()).selectedPlace

        val application = requireNotNull(this.activity).application

        val viewModelFactory = ResultsViewModelFactory(selectedPlace, application)

        resultsViewModel = ViewModelProvider(this, viewModelFactory).get(ResultsViewModel::class.java)

        resultsViewModel.getWeatherResults(selectedPlace.name.toString())

        Log.i(TAG, "onCreate complete")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        /**
         * View bindings
         */

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_results, container, false)

        binding.viewModel = resultsViewModel

        binding.lifecycleOwner = this

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

        val currentDate = LocalDate.now()

        dateText.text = currentDate.toString()

        setupMap()

        Log.i(TAG, "View bindings set")

        resultsViewModel.navigateToLandingPage.observe(viewLifecycleOwner) {
            if (it == true) {
                this.findNavController().navigate(
                    ResultsFragmentDirections.actionResultsFragmentToLandingPage()
                )
                resultsViewModel.onNavigated()
            }
        }

        return binding.root
    }

    /**
     * Maps
     */

    private fun updateMap(map: GoogleMap) {
//        if (mapReady)
//        {
            val marker = selectedPlace.latLng!!
            val zoom = 6.0F
            populateMap(map, marker)
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(marker, zoom))
            Log.i(TAG, "Map marker placed at: $marker")
//        }
    }

    @DelicateCoroutinesApi
    private fun setupMap()
    {
        val mapFragment = childFragmentManager.findFragmentById(
            R.id.map_results) as SupportMapFragment?

        mapFragment?.getMapAsync{
                googleMap -> map = googleMap
            onMapReady(googleMap)
        }

        Log.i(TAG, "Map ready.")
    }
    override fun onMapReady(googleMap : GoogleMap)
    {
        updateMap(googleMap)
        Log.i(TAG, "onMapReady accessed")
    }

    private fun populateMap(map : GoogleMap, location: LatLng) {

            map.addMarker(
                MarkerOptions()
                    .position(location))
                map.moveCamera(CameraUpdateFactory.newLatLng(location))
            Log.i(TAG, "Map updated.")
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