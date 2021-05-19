package com.example.feelslike.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceFragmentCompat
import com.example.feelslike.R
import com.example.feelslike.view_model.LoginViewModel

class SettingsFragment : PreferenceFragmentCompat()
{
    companion object
    {
        const val TAG = "SettingsFragment"
    }

    // Get a reference to the ViewModel scoped to this Fragment
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?)
    {
        setPreferencesFromResource(R.xml.settings, rootKey)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**
         * Observe the authenticationState and redirect the user to [LoginFragment]
         * if they are not authenticated
         */
        val navController = findNavController()
        viewModel.authenticationState.observe(viewLifecycleOwner, Observer { authenticationState ->
            when (authenticationState)
            {
                LoginViewModel.AuthenticationState.AUTHENTICATED -> Log.i(TAG, "Authenticated")

                LoginViewModel.AuthenticationState.UNAUTHENTICATED -> navController.navigate(
                    R.id.loginFragment
                )
                else -> Log.e(
                    TAG, "New $authenticationState state that doesn't require any UI change"
                )
            }
        })
    }
}