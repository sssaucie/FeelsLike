package com.example.feelslike.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.feelslike.R
import com.example.feelslike.databinding.FragmentProfileBinding
import com.example.feelslike.model.database.FeelsLikeDatabase
import com.example.feelslike.utilities.FavoritesAdapter
import com.example.feelslike.utilities.FavoritesListClickListener
import com.example.feelslike.view_model.FavoritesViewModel
import com.example.feelslike.view_model.FavoritesViewModelFactory

class FavoritesFragment : Fragment()
{
    private lateinit var binding : FragmentProfileBinding
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        // View bindings for RecyclerView
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        binding.profileRecyclerViewFavorites.layoutManager =
            LinearLayoutManager(requireContext())
        binding.profileRecyclerViewFavorites.adapter =
            FavoritesAdapter()

        return binding.root
    }
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//
//        val binding : FragmentProfileBinding = DataBindingUtil.inflate(
//            inflater, R.layout.fragment_profile, container, false)
//
//        val application = requireNotNull(this.activity).application
//
//        val dataSource = FeelsLikeDatabase.getInstance(application).calculationsDao()
//
//        val viewModelFactory = FavoritesViewModelFactory(dataSource, application)
//
//        val favoritesViewModel =
//            ViewModelProvider(
//                this, viewModelFactory).get(FavoritesViewModel::class.java)
//
//        binding.viewModel = favoritesViewModel
//
//        binding.lifecycleOwner(this)
//
//        favoritesViewModel.navigateToMaps.observe(viewLifecycleOwner, { place ->
//            place?.let {
//                this.findNavController().navigate(
//                    FavoritesFragmentDirections
//                        .actionFavoritesFragmentToMapsFragment(currentSelection = place))
//                favoritesViewModel.onNavigated()
//            }
//        })
//
//        val adapter = FavoritesAdapter(FavoritesListClickListener { id ->
//            favoritesViewModel.onFavoritePlaceClicked(id)
//        })
//
//        binding.profileRecyclerViewFavorites.adapter = adapter

        /**
         * Observe for new data
         */

//        favoritesViewModel.place.observe(viewLifecycleOwner, {
//            it?.let {
//                adapter.submitList(it)
//            }
//        })

//        return binding.root
//    }
}