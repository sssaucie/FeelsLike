package com.emilycodes.feelslike.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.emilycodes.feelslike.R
import com.emilycodes.feelslike.databinding.FragmentDialogPlannedLocationFormBinding
import com.emilycodes.feelslike.view_model.PlannedLocationViewModel
import com.emilycodes.feelslike.view_model.PlannedLocationViewModelFactory
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener

class PlannedLocationFragment : Fragment()
{
    private lateinit var binding : FragmentDialogPlannedLocationFormBinding
    private val TAG = "PlannedLocationFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_dialog_planned_location_form, container, false)

        val application = requireNotNull(this.activity).application

        val viewModelFactory = PlannedLocationViewModelFactory(application)

        val plannedLocationViewModel =
            ViewModelProvider(this, viewModelFactory)
                .get(PlannedLocationViewModel::class.java)

        binding.viewModel = plannedLocationViewModel

        binding.lifecycleOwner = this
        // TODO: Get background dialog window asset from James
//        dialog.window?.setBackgroundDrawableResource(R.drawable.shape_dialog_fragment_corners)

        // Initialize the AutocompleteSupportFragment
        val autocompleteFragment = childFragmentManager.findFragmentById(
            R.id.autocomplete_fragment) as AutocompleteSupportFragment

        // Specify the types of place data to return
        autocompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS))

        // Set up a PlaceSelectionListener to handle the response
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener
        {
            override fun onPlaceSelected(place : Place)
            {
                // TODO: Get info about the selected place
                Log.i(TAG, "Place: ${place.name}, ${place.id}, ${place.address}")
            }

            override fun onError(status : Status)
            {
//                TODO: Handle the error
                Log.i(TAG, "An error occurred: $status")
            }
        })

        plannedLocationViewModel.navigateToLandingPage.observe(viewLifecycleOwner, {
            if (it == true)
            {
                this.findNavController().navigate(
                    PlannedLocationFragmentDirections
                        .actionPlannedLocationWidgetToLandingPage()
                )
                plannedLocationViewModel.doneNavigating()
            }
        })

        plannedLocationViewModel.navigateExitToLandingPage.observe(viewLifecycleOwner, {
            if (it == true)
            {
                this.findNavController().navigate(
                    PlannedLocationFragmentDirections
                        .actionPlannedLocationWidgetExitToLandingPage()
                )
                plannedLocationViewModel.doneNavigating()
            }
        })
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        // Height is optional here and is not used - in favor of wrapping content
        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
//        dialog?.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }
}