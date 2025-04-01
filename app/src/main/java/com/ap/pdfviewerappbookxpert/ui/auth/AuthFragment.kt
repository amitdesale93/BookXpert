package com.ap.pdfviewerappbookxpert.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ap.pdfviewerappbookxpert.R
import com.ap.pdfviewerappbookxpert.Viewmodels.AuthViewModel
import com.ap.pdfviewerappbookxpert.model.ResponseState
import com.ap.pdfviewerappbookxpert.utils.Constants.RC_SIGN_IN
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AuthFragment : Fragment() {
    private val viewModel: AuthViewModel by viewModels()
@Inject   lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_auth, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<SignInButton>(R.id.btnGoogleSignIn).setOnClickListener {
            signInWithGoogle()
        }
    }

    private fun signInWithGoogle() {

        googleSignInClient.signOut()
        val signInIntent: Intent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {

            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                if (account != null) {
                    getGoogleAuthCredential(account)
                }
            } catch (e: ApiException) {
                Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getGoogleAuthCredential(account: GoogleSignInAccount) {
        val googleTokeId = account.idToken
        val googleAuthCredential = GoogleAuthProvider.getCredential(googleTokeId, null)
        signInWithGoogleAuthCredential(googleAuthCredential)
    }

    private fun signInWithGoogleAuthCredential(googleAuthCredential: AuthCredential) {

        viewModel.signInWithGoogle(googleAuthCredential)
        viewModel.authenticateUserLiveData.observe(viewLifecycleOwner) { authenticatedUser ->
            when (authenticatedUser) {
                is ResponseState.Error -> {
                    Toast.makeText(requireContext(),authenticatedUser.message, Toast.LENGTH_SHORT).show()

                }
                is ResponseState.Success -> {
                    if (authenticatedUser.data != null) {
                        Toast.makeText(requireContext(),"Sign in Successfull Data saved in room database", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_auth_to_home)
                    }
                }
                is ResponseState.Loading -> {
                }
            }
        }
    }
}
