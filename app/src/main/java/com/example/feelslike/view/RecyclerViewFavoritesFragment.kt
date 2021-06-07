package com.example.feelslike.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.feelslike.databinding.FragmentProfileBinding
import com.example.feelslike.utilities.RecyclerViewFavoritesAdapter

class RecyclerViewFavoritesFragment : Fragment()
{
    private lateinit var binding : FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val adapter = RecyclerViewFavoritesAdapter()
        binding = FragmentProfileBinding.inflate(
            inflater, container, false)

        binding.profileRecyclerViewFavorites.layoutManager =
            LinearLayoutManager(requireContext())

        binding.profileRecyclerViewFavorites.adapter = RecyclerViewFavoritesAdapter()

        return binding.root
    }
}