package com.emilycodes.feelslike.view

import androidx.fragment.app.Fragment


class InitialUserInputFragment : Fragment() {}
//{
//    private lateinit var binding: FragmentInitialUserInputBinding
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        binding = DataBindingUtil.inflate(
//            inflater, R.layout.fragment_initial_user_input, container, false
//        )
//
//        val application = requireNotNull(this.activity).application
//
//        val viewModelFactory = InitialUserInputViewModelFactory(application)
//
//        val initialUserInputViewModel =
//            ViewModelProvider(this, viewModelFactory)
//                .get(InitialUserInputViewModel::class.java)
//
//        val continueButton = binding.inputButtonContinue
//
//        continueButton.isEnabled = false
//
//        binding.viewModel = initialUserInputViewModel
//
//        binding.lifecycleOwner = this
//
//        binding.inputCheckboxMeasurementType.setOnCheckedChangeListener { _, isChecked ->
//
//            val list = listOf(
//                binding.descriptorHeightFeet,
//                binding.descriptorHeightInches,
//                binding.inputHeightInches,
//                binding.descriptorWeightPounds
//            )
//
//            if (isChecked) {
//                metricVisibility(list, false)
//                binding.descriptorHeightCentimeters.visibility = View.VISIBLE
//                binding.descriptorWeightKilograms.visibility = View.VISIBLE
//            } else {
//                metricVisibility(list, true)
//                binding.descriptorHeightCentimeters.visibility = View.GONE
//                binding.descriptorWeightKilograms.visibility = View.GONE
//            }
//        }
//
//        initialUserInputViewModel.navigateToLandingPage.observe(viewLifecycleOwner, {
//            if (it == true) {
//                this.findNavController().navigate(
//                    InitialUserInputFragmentDirections
//                        .actionInitialUserInputFragmentToLandingPage()
//                )
//                initialUserInputViewModel.doneNavigating()
//            }
//        })
//
//        initialUserInputViewModel.navigateSkipToLandingPage.observe(viewLifecycleOwner, {
//            if (it == true) {
//                this.findNavController().navigate(
//                    InitialUserInputFragmentDirections
//                        .actionInitialUserInputFragmentSkipToLandingPage()
//                )
//                initialUserInputViewModel.doneNavigating()
//            }
//        })
//
//        return binding.root
//    }
//
//    private fun metricVisibility(list: List<View>, show: Boolean) {
//        if (show) {
//            for (view in list) {
//                view.visibility = View.VISIBLE
//            }
//        } else {
//            for (view in list) {
//                view.visibility = View.GONE
//            }
//        }
//    }
//}
//        val editText = listOf(
//            binding.editEmail,
//            binding.editHeightFeetCentimeters,
//            binding.editFirstName,
//            binding.editLastName,
//            binding.editWeight)
//    fun showSoftKeyboard(view: View) {
//        if (view.requestFocus()) {
//            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
//        }
//    }
//
//        for (complete in editText)
//        {
//            complete.addTextChangedListener(object : TextWatcher
//            {
//                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//
//                }
//
//                override fun beforeTextChanged(
//                    s: CharSequence?,
//                    start: Int,
//                    count: Int,
//                    after: Int
//                ) {
//                    continueButton.isEnabled = false
//                }
//
//                override fun afterTextChanged(s: Editable?) {
//                    val firstName = binding.editFirstName.text.toString() ?: "foo"
//                    val lastName = binding.editLastName.text.toString() ?: "foo"
//                    val emailAddress = binding.editEmail.text.toString()
//                    val heightFeetCentimeters = binding.editHeightFeetCentimeters.text.toString()
//                    val weight = binding.editWeight.text.toString()
//
//                    continueButton.isEnabled = firstName.isNotEmpty()
//                            && lastName.isNotEmpty()
//                            && emailAddress.isNotEmpty()
//                            && heightFeetCentimeters.isNotEmpty()
//                            && weight.isNotEmpty()
//                }
//            })
//        }
//                val firstName = binding.editFirstName.text.toString()
//                val lastName = binding.editLastName.text.toString()
//                val emailAddress = binding.editEmail.text.toString()
//                val isMetric = binding.inputCheckboxMeasurementType.isChecked
//                val heightFeetCentimeters = binding.editHeightFeetCentimeters.text.toString().toInt()
//                val heightInches = binding.editHeightInches.text.toString().toInt()
//                val weight = binding.editWeight.text.toString().toFloat()
//