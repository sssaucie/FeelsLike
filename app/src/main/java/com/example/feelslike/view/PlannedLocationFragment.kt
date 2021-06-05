package com.example.feelslike.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.feelslike.R
import com.example.feelslike.databinding.FragmentDialogPlannedLocationFormBinding
import com.example.feelslike.model.database.FeelsLikeDatabase
import com.example.feelslike.view_model.PlannedLocationViewModel
import com.example.feelslike.view_model.PlannedLocationViewModelFactory

class PlannedLocationFragment : DialogFragment()
//{
//    private lateinit var binding : FragmentDialogPlannedLocationFormBinding
//    private val viewModel : PlannedLocationViewModel by lazy {
//        ViewModelProvider(this).get(PlannedLocationViewModel::class.java)
//    }
//
//// Initialize the AutocompleteSupportFragment.
//val autocompleteFragment =
//    supportFragmentManager.findFragmentById(R.id.autocomplete_fragment)
//            as AutocompleteSupportFragment
//
//// Specify the types of place data to return.
//autocompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME))
//
//// Set up a PlaceSelectionListener to handle the response.
//autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
//    override fun onPlaceSelected(place: Place) {
//        // TODO: Get info about the selected place.
//        Log.i(TAG, "Place: ${place.name}, ${place.id}")
//    }
//
//    override fun onError(status: Status) {
//        // TODO: Handle the error.
//        Log.i(TAG, "An error occurred: $status")
//    }
//})
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        binding = DataBindingUtil.inflate(
//            inflater, R.layout.fragment_dialog_planned_location_form, container, false)
//
//        val application = requireNotNull(this.activity).application
//
//        val dataSource = FeelsLikeDatabase.getInstance(application).calculationsDao()
//
//        val viewModelFactory = PlannedLocationViewModelFactory(dataSource, application)
//
//        val plannedLocationViewModel =
//            ViewModelProvider(this, viewModelFactory)
//                .get(PlannedLocationViewModel::class.java)
//
//        binding.viewModel = plannedLocationViewModel
//
//        binding.lifecycleOwner = this
//        // TODO: Get background dialog window asset from James
//        dialog?.window?.setBackgroundDrawableResource(R.drawable.shape_dialog_fragment_corners)
//
//        plannedLocationViewModel.navigateToLandingPage.observe(viewLifecycleOwner, {
//            if (it == true)
//            {
//                this.findNavController().navigate(
//                    PlannedLocationFragmentDirections
//                        .actionPlannedLocationWidgetToLandingPage()
//                )
//                plannedLocationViewModel.doneNavigating()
//            }
//        })
//
//        plannedLocationViewModel.navigateExitToLandingPage.observe(viewLifecycleOwner, {
//            if (it == true)
//            {
//                this.findNavController().navigate(
//                    PlannedLocationFragmentDirections
//                        .actionPlannedLocationWidgetEXITToLandingPage()
//                )
//                plannedLocationViewModel.doneNavigating()
//            }
//        })
//        return binding.root
//    }
//
//    override fun onStart() {
//        super.onStart()
//        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
//        // Height is optional here and is not used - in favor of wrapping content
//        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
//        dialog?.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
//    }
//}