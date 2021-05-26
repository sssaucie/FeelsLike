package com.example.feelslike.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.feelslike.R
import com.example.feelslike.databinding.FragmentInitialUserInputBinding
import com.example.feelslike.model.database.FeelsLikeDatabase
import com.example.feelslike.utilities.InitialUserInputAdapter
import com.example.feelslike.view_model.InitialUserInputViewModel
import com.example.feelslike.view_model.InitialUserInputViewModelFactory


class InitialUserInputFragment : Fragment()
{
    private lateinit var binding: FragmentInitialUserInputBinding

    private val viewModel : InitialUserInputViewModel by lazy {
        ViewModelProvider(this).get(InitialUserInputViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View
    {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_initial_user_input, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = FeelsLikeDatabase.getInstance(application).userDao()

        val viewModelFactory = InitialUserInputViewModelFactory(dataSource, application)

        val initialUserInputViewModel =
            ViewModelProvider(this, viewModelFactory)
                .get(InitialUserInputViewModel::class.java)

        val adapter = InitialUserInputAdapter()

        binding.viewModel = initialUserInputViewModel

        binding.lifecycleOwner = this

        fun onMetricsCheckboxClicked(view: View)
{
            if (view is CheckBox)
            {
                val checked : Boolean = view.isChecked

                when (view.id)
                {
                    R.id.input_checkbox_measurement_type -> {
                        if (checked)
                        {
                            binding.descriptorHeightFeet.visibility = View.GONE
                            binding.descriptorHeightInches.visibility = View.GONE
                            binding.inputHeightInches.visibility = View.GONE
                            binding.descriptorHeightCentimeters.visibility = View.VISIBLE
                        }
                        else
                        {
                            binding.descriptorHeightCentimeters.visibility = View.GONE
                        }
                    }
                }
            }
        }

        initialUserInputViewModel.navigateToLandingPage.observe(viewLifecycleOwner, {
            if (it == true)
            {
                val firstName = binding.editFirstName.text
                val lastName = binding.editLastName.text
                val emailAddress = binding.editEmail.text
                val heightFeetCentimeters = binding.editHeightFeetCentimeters.text
                val heightInches = binding.editHeightInches.text
                val weight = binding.editWeight.text

//                viewModel.createEntity(foo, bar)

                this.findNavController().navigate(
                    InitialUserInputFragmentDirections.actionInitialUserInputFragmentToLandingPage())
                initialUserInputViewModel.doneNavigating()
            }
        })

        return binding.root
    }
}
