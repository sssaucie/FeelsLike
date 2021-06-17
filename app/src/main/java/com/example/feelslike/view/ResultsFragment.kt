package com.example.feelslike.view

import android.app.AlertDialog
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.feelslike.R
import com.example.feelslike.databinding.FragmentResultsBinding
import com.example.feelslike.model.entity.CalculationsEntity
import com.example.feelslike.utilities.FeelsLikeRepository
import com.example.feelslike.utilities.MapsService
import com.example.feelslike.view_model.SharedViewModelFactory
import com.example.feelslike.view_model.SharedViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class ResultsFragment : Fragment()
{
    private lateinit var selectedPlace : CalculationsEntity
    private lateinit var dataRepo : FeelsLikeRepository
    private lateinit var map : GoogleMap
    private lateinit var mapsService : MapsService
    private var TAG = ResultsFragment::class.java.simpleName
    private var mapReady = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
//        selectedPlace = ResultsFragmentArgs.fromBundle(requireArguments()).desiredPlace

        val binding : FragmentResultsBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_results, container, false)

        val application = requireNotNull(this.activity).application

        val viewModelFactory =
            binding.viewModel?.intent?.let { SharedViewModelFactory(application, it) }

        val sharedViewModel =
            viewModelFactory?.let {
                ViewModelProvider(
                    this, it
                ).get(SharedViewModel::class.java)
            }

        binding.viewModel = sharedViewModel

        binding.lifecycleOwner = this

        sharedViewModel?.navigateToRecyclerViewFavorites?.observe(viewLifecycleOwner,
            {
                if (it == true)
                {
//                    this.findNavController().navigate(
                        // TODO: Navigate to favorites when we have favorites
//                    )
//                    sharedViewModel.onNavigated()
                }
            })

        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map_layout) as? SupportMapFragment
        mapFragment?.getMapAsync{
                googleMap -> map = googleMap
            mapReady = true
            updateMap(googleMap)
        }

        mapsService = MapsService.getInstance()

        return binding.root
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
}