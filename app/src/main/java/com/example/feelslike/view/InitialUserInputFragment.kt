package com.example.feelslike.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.feelslike.R
import com.example.feelslike.databinding.FragmentInitialUserInputBinding
import com.example.feelslike.model.database.FeelsLikeDatabase
import com.example.feelslike.view_model.InitialUserInputViewModel
import com.example.feelslike.view_model.InitialUserInputViewModelFactory


class InitialUserInputFragment : Fragment()
{
    private lateinit var binding: FragmentInitialUserInputBinding

    private val viewModel: InitialUserInputViewModel by lazy {
        ViewModelProvider(this).get(InitialUserInputViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View
    {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_initial_user_input, container, false
        )

        val application = requireNotNull(this.activity).application

        val dataSource = FeelsLikeDatabase.getInstance(application).userDao()

        val viewModelFactory = InitialUserInputViewModelFactory(dataSource, application)

        val initialUserInputViewModel =
            ViewModelProvider(this, viewModelFactory)
                .get(InitialUserInputViewModel::class.java)

        val editText = listOf(
            binding.editEmail,
            binding.editHeightFeetCentimeters,
            binding.editFirstName,
            binding.editLastName,
            binding.editWeight)

        val continueButton = binding.inputButtonContinue

        continueButton.isEnabled = false

        for (complete in editText)
        {
            complete.addTextChangedListener(object : TextWatcher
            {
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    continueButton.isEnabled = false
                }

                override fun afterTextChanged(s: Editable?) {
                     val firstName = binding.editFirstName.text.toString() ?: "foo"

                    val lastName = binding.editLastName.text.toString() ?: "foo"
                    val emailAddress = binding.editEmail.text.toString()
                    val heightFeetCentimeters = binding.editHeightFeetCentimeters.text.toString()
                    val weight = binding.editWeight.text.toString()

                    continueButton.isEnabled = firstName.isNotEmpty()
                            && lastName.isNotEmpty()
                            && emailAddress.isNotEmpty()
                            && heightFeetCentimeters.isNotEmpty()
                            && weight.isNotEmpty()
                }
            })
        }

        binding.viewModel = initialUserInputViewModel

        binding.lifecycleOwner = this

        binding.inputCheckboxMeasurementType.setOnCheckedChangeListener { _, isChecked ->

            val list = listOf<View>(
                binding.descriptorHeightFeet,
                binding.descriptorHeightInches,
                binding.inputHeightInches,
                binding.descriptorWeightPounds
            )

            if (isChecked) {
                metricVisibility(list, false)
                binding.descriptorHeightCentimeters.visibility = View.VISIBLE
                binding.descriptorWeightKilograms.visibility = View.VISIBLE
            } else {
                metricVisibility(list, true)
                binding.descriptorHeightCentimeters.visibility = View.GONE
                binding.descriptorWeightKilograms.visibility = View.GONE
            }
        }



        initialUserInputViewModel.navigateToLandingPage.observe(viewLifecycleOwner, {
            if (it == true) {
                val firstName = binding.editFirstName.text.toString()
                val lastName = binding.editLastName.text.toString()
                val emailAddress = binding.editEmail.text.toString()
                val isMetric = binding.inputCheckboxMeasurementType.isChecked
                val heightFeetCentimeters = binding.editHeightFeetCentimeters.text.toString().toInt()
                val heightInches = binding.editHeightInches.text.toString().toInt()
                val weight = binding.editWeight.text.toString().toFloat()

                viewModel.createUser(
                    firstName,
                    lastName,
                    emailAddress,
                    isMetric,
                    heightFeetCentimeters,
                    heightInches,
                    weight)

                this.findNavController().navigate(
                    InitialUserInputFragmentDirections
                        .actionInitialUserInputFragmentToLandingPage()
                )
                initialUserInputViewModel.doneNavigating()
            }
        })

        initialUserInputViewModel.navigateSkipToLandingPage.observe(viewLifecycleOwner, {
            if (it == true)
            {
                this.findNavController().navigate(
                    InitialUserInputFragmentDirections
                        .actionInitialUserInputFragmentSkipToLandingPage()
                )
                initialUserInputViewModel.doneNavigating()
            }
        })

        return binding.root
    }

    private fun metricVisibility(list:List<View>, show:Boolean)
    {
        if(show)
        {
            for(view in list)
            {
                view.visibility = View.VISIBLE
            }
        }
        else
        {
            for(view in list)
            {
                view.visibility = View.GONE
            }
        }
    }

//    fun showSoftKeyboard(view: View) {
//        if (view.requestFocus()) {
//            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
//        }
//    }
}
