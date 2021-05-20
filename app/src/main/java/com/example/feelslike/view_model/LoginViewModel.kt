package com.example.feelslike.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginViewModel : ViewModel()
{
    companion object
    {
        private const val TAG = "EmailPassword"
    }

    enum class AuthenticationState
    {
        AUTHENTICATED, UNAUTHENTICATED, INVALID_AUTHENTICATION
    }

    val authenticationState = FirebaseUserLiveData().map { firebaseUser ->
        if (firebaseUser != null)
        {
            AuthenticationState.AUTHENTICATED
        }
        else
        {
            AuthenticationState.UNAUTHENTICATED
        }
    }
}

/**
 * This class observes the current FirebaseUser. If there is no logged in user, FirebaseUser will
 * be null.
 *
 * Note that onActive() and onInactive() will get triggered when the configuration changes (for
 * example when the device is rotated). This may be undesirable or expensive depending on the
 * nature of your LiveData object, but is okay for this purpose since we are only adding and
 * removing the authStateListener.
 */
class FirebaseUserLiveData : LiveData<FirebaseUser?>()
{
    private val firebaseAuth = FirebaseAuth.getInstance()

    // TODO set the value of this FireUserLiveData object by hooking it up to equal the value of the
    //  current FirebaseUser. You can utilize the FirebaseAuth.AuthStateListener callback to get
    //  updates on the current Firebase user logged into the app.
    private val authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
        // Use the FirebaseAuth instance instantiated at the beginning of the class to get an
        // entry point into the Firebase Authentication SDK the app is using.
        // With an instance of the FirebaseAuth class, you can now query for the current user.
        value = firebaseAuth.currentUser
    }

    // When this object has an active observer, start observing the FirebaseAuth state to see if
    // there is currently a logged in user.
    override fun onActive() {
        firebaseAuth.addAuthStateListener(authStateListener)
    }

    // When this object no longer has an active observer, stop observing the FirebaseAuth state to
    // prevent memory leaks.
    override fun onInactive() {
        firebaseAuth.removeAuthStateListener(authStateListener)
    }
}