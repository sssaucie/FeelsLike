package com.example.feelslike.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.feelslike.R
import com.example.feelslike.databinding.FragmentLandingPageBinding
import com.example.feelslike.model.dao.CalculationsDao
import com.example.feelslike.model.dao.FavoritesDao
import com.example.feelslike.model.dao.UserDao
import com.example.feelslike.model.database.FeelsLikeDatabase
import com.example.feelslike.view_model.LandingPageViewModel
import com.example.feelslike.view_model.LandingPageViewModelFactory

class LandingPageFragment : Fragment()
{
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding : FragmentLandingPageBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_landing_page, container, false)

        val application = requireNotNull(this.activity).application
        val userDataSource = FeelsLikeDatabase.getInstance(application).userDao()
        val calculationsDataSource = FeelsLikeDatabase.getInstance(application).calculationsDao()

        val viewModelFactory = LandingPageViewModelFactory(
            userDataSource, calculationsDataSource, application)

        val landingPageViewModel =
            ViewModelProvider(
                this, viewModelFactory).get(LandingPageViewModel::class.java)

        binding.viewModel = landingPageViewModel

        binding.lifecycleOwner = this

        landingPageViewModel.navigateToProfileFragment.observe(viewLifecycleOwner, {
            if (it == true)
            {
                this.findNavController().navigate(
                    LandingPageFragmentDirections.actionLandingPageToProfileFragment())
                landingPageViewModel.onNavigated()
            }
        })
        return binding.root
    }
}