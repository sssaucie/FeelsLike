package com.example.feelslike.view

import android.app.AlertDialog
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.feelslike.R
import com.example.feelslike.databinding.FragmentResultsBinding
import com.example.feelslike.view_model.ResultsViewModel
import com.example.feelslike.view_model.ResultsViewModelFactory

class ResultsFragment : Fragment()
{
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        val binding : FragmentResultsBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_results, container, false)

        val application = requireNotNull(this.activity).application

        val viewModelFactory = ResultsViewModelFactory(application)

        val resultsViewModel =
            ViewModelProvider(
                this, viewModelFactory).get(ResultsViewModel::class.java)

        binding.viewModel = resultsViewModel
        binding.lifecycleOwner = this

        resultsViewModel.navigateToRecyclerViewFavorites.observe(viewLifecycleOwner,
            {
                if (it == true)
                {
                    this.findNavController().navigate(
                        ResultsFragmentDirections.actionResultsFragmentToFavoritesFragment())
                    resultsViewModel.onNavigated()
                }
            })

        return binding.root
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
    }
}