package com.example.feelslike.view

import androidx.fragment.app.Fragment

class WidgetMenuProfileFragment : Fragment()
//{
//    private lateinit var application : Application
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//
//        val binding : WidgetMenuProfilePicBinding = DataBindingUtil.inflate(
//            inflater, R.layout.widget_menu_profile_pic, container, false)
//
//        val viewModelFactory = WidgetMenuProfileViewModelFactory(application)
//
//        val widgetMenuProfileViewModel =
//            ViewModelProvider(
//                this, viewModelFactory).get(WidgetMenuProfileViewModel::class.java)
//
//        binding.viewModel = widgetMenuProfileViewModel
//
//        binding.lifecycleOwner = this
//
//        widgetMenuProfileViewModel.navigateToProfileFragment.observe(viewLifecycleOwner, {
//            if (it == true)
//            {
//                this.findNavController().navigate(
//                    WidgetMenuProfileFragmentDirections.actionProfilePicToProfileFragment())
//                widgetMenuProfileViewModel.onNavigated()
//            }
//        })
//
//        return binding.root
//    }
//
//}