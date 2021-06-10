package com.example.feelslike.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.feelslike.R
import com.example.feelslike.databinding.FragmentLandingPageBinding
import com.example.feelslike.utilities.LocationPermissions
import com.example.feelslike.view_model.SharedViewModel
import com.example.feelslike.view_model.SharedViewModelFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import kotlinx.coroutines.DelicateCoroutinesApi

class LandingPageFragment : BaseMapsFragment(), OnMapReadyCallback
{
    private lateinit var locationPermissions : LocationPermissions

    @DelicateCoroutinesApi
    override val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera. In this case,
         * we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to install
         * it inside the SupportMapFragment. This method will only be triggered once the user has
         * installed Google Play services and returned to the app.
         */
        onMapReady(googleMap)
    }

    @DelicateCoroutinesApi
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

        binding.viewModel = sharedViewModel

        binding.widgetLocationCalculateButtons.viewModelLandingPage = sharedViewModel

        binding.headerLandingPageMenuProfile.viewModel = sharedViewModel

        binding.landingPageMap.mapsLayout

        binding.lifecycleOwner = this

        sharedViewModel.navigateToResultsFragment.observe(viewLifecycleOwner, {
            if (it == true)
            {
                this.findNavController().navigate(
                    LandingPageFragmentDirections.actionLandingPageToResultsFragment())
                sharedViewModel.onNavigated()
            }
        })

        sharedViewModel.navigateToProfileFragment.observe(viewLifecycleOwner, {
            if (it == true)
            {
                this.findNavController().navigate(
                    LandingPageFragmentDirections.actionLandingPageToProfileFragment())
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

        sharedViewModel.navigateToMapFragment.observe(viewLifecycleOwner, {
            if (it == true)
            {
                this.findNavController().navigate(
                    LandingPageFragmentDirections.actionLandingPageToMapsFragment())
                sharedViewModel.onNavigated()
            }
        })

        return binding.root
    }

    @DelicateCoroutinesApi
    override fun onMapReady(googleMap : GoogleMap)
    {
        super.onMapReady(googleMap)

         // set customizations: camera, scale, location, style, markers
    }

    @DelicateCoroutinesApi
    override fun onResume()
    {
        locationPermissions.getCurrentLocation()
        getMapAsync(callback)
        super.onResume()
    }
}