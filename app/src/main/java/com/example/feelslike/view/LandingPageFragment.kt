package com.example.feelslike.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.feelslike.R
import com.example.feelslike.databinding.FragmentLandingPageBinding
import com.example.feelslike.view_model.SharedViewModel
import com.example.feelslike.view_model.SharedViewModelFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import kotlinx.coroutines.DelicateCoroutinesApi

class LandingPageFragment : Fragment(), OnMapReadyCallback
{
//    private lateinit var map : GoogleMap
//    private lateinit var mapsFragment : MapsFragment
    private var TAG = LandingPageFragment::class.java.simpleName

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding : FragmentLandingPageBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_landing_page, container, false)

        val application = requireNotNull(this.activity).application

        val viewModelFactory = SharedViewModelFactory(application)

        val sharedViewModel =
            ViewModelProvider(
                this, viewModelFactory).get(SharedViewModel::class.java)

        val mapFragment = childFragmentManager.findFragmentById(
            R.id.map_layout) as SupportMapFragment?

        mapFragment?.getMapAsync(this)

        binding.viewModel = sharedViewModel

        binding.widgetLocationCalculateButtons.viewModelLandingPage = sharedViewModel

        binding.headerLandingPageMenuProfile.viewModel = sharedViewModel

        binding.lifecycleOwner = this

        sharedViewModel.navigateToResultsFragment.observe(viewLifecycleOwner, { selectedPlace ->
        selectedPlace?.let {
                this.findNavController().navigate(
                    LandingPageFragmentDirections.actionLandingPageToResultsFragment(selectedPlace))
                sharedViewModel.onNavigated()
            }
        })

        sharedViewModel.navigateToPlannedLocationFragment.observe(viewLifecycleOwner, {
            if (it == true)
            {
                this.findNavController().navigate(
                    LandingPageFragmentDirections.actionLandingPageToPlannedLocationWidget())
                sharedViewModel.onNavigated()
            }
        })

        return binding.root
    }

    @DelicateCoroutinesApi
    override fun onMapReady(googleMap : GoogleMap)
    {
//        map = googleMap
//        mapsFragment.onMapReady(map)
        Log.i(TAG, "onMapReady accessed")
    }
}