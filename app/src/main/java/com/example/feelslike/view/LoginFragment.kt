package com.example.feelslike.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.feelslike.MainActivity
import com.example.feelslike.view.LoginFragment.Companion.TAG
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginFragment : Fragment()
{
    private lateinit var firebaseAuth : FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
        firebaseAuth = LoginFragmentArgs.fromBundle(requireArguments()).firebaseAuth
    }

    public override fun onStart()
    {
        super.onStart()
        val currentUser = firebaseAuth.currentUser
        if (currentUser != null)
        {
            reload();
        }
    }

    private fun createAccount(email: String, password: String)
    {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful)
                {
                    Log.d(MainActivity.TAG, "createUserWithEmail:success")
                    val user = firebaseAuth.currentUser
                    updateUI(user)
                } else
                {
                    Log.w(MainActivity.TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }

    private fun signIn(email: String, password: String)
    {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful)
                {
                    Log.d(MainActivity.TAG, "signInWithEmail:success")
                    val user = firebaseAuth.currentUser
                    updateUI(user)
                } else
                {
                    Log.w(MainActivity.TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }

    private fun sendEmailVerification()
    {
        val user = firebaseAuth.currentUser!!
        user.sendEmailVerification()
            .addOnCompleteListener(this) { task ->
                // Email verification sent
            }
    }

    private fun updateUI(user: FirebaseUser?)
    {

    }

    private fun reload()
    {

    }

    companion object
    {
        private const val TAG = "EmailPassword"
    }
}