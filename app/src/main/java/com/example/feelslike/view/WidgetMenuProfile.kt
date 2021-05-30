package com.example.feelslike.view

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.feelslike.R
import com.example.feelslike.model.entity.CalculationsEntity
import com.example.feelslike.model.entity.UserEntity
import com.example.feelslike.view_model.WidgetMenuProfileViewModel
import com.example.feelslike.view_model.WidgetMenuProfileViewModelFactory

class WidgetMenuProfile : Fragment()
{
    private lateinit var user : UserEntity
    private lateinit var calculations : CalculationsEntity
    private lateinit var application : Application

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        user = WidgetMenuProfileArgs.fromBundle(requireArguments()).user

        val binding :  = DataBindingUtil.inflate(
            inflater, R.layout.widget_menu_profile_pic, container, false)

        val viewModelFactory = WidgetMenuProfileViewModelFactory(user, calculations, application)

        val widgetMenuProfileViewModel =
            ViewModelProvider(
                this, viewModelFactory).get(WidgetMenuProfileViewModel::class.java)

        binding.viewModel = widgetMenuProfileViewModel

        binding.lifecycleOwner = this

        widgetMenuProfileViewModel.navigateToProfileFragment.observe(viewLifecycleOwner, { user ->
            user?.let {
                this.findNavController().navigate(
                    WidgetMenuProfileDirections.actionProfilePicToProfileFragment(user))
                widgetMenuProfileViewModel.onNavigated()
            }
        })

        return binding.root
    }

}