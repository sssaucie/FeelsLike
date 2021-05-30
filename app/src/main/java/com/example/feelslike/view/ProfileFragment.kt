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
import com.example.feelslike.databinding.FragmentProfileBinding
import com.example.feelslike.model.entity.UserEntity
import com.example.feelslike.view_model.ProfileViewModel
import com.example.feelslike.view_model.ProfileViewModelFactory

class ProfileFragment : Fragment()
{
    private lateinit var user : UserEntity
    private lateinit var binding : FragmentProfileBinding

    private val viewModel : ProfileViewModel by lazy {
        ViewModelProvider(this).get(ProfileViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_profile, container, false)

        user = ProfileFragmentArgs.fromBundle(requireArguments()).user

        val application = requireNotNull(this.activity).application

        val viewModelFactory = ProfileViewModelFactory(user, application)

        val profileViewModel =
            ViewModelProvider(
                this, viewModelFactory).get(ProfileViewModel::class.java)

        binding.viewModel = profileViewModel

        binding.lifecycleOwner = this

        profileViewModel.navigateToLandingPage.observe(viewLifecycleOwner, {
            if (it == true)
            {
                this.findNavController().navigate(
                    ProfileFragmentDirections.actionProfileFragmentToLandingPage())
                profileViewModel.onNavigated()
            }
        })

        return binding.root
    }
}