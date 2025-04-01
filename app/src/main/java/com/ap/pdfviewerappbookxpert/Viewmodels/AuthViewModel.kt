package com.ap.pdfviewerappbookxpert.Viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ap.pdfviewerappbookxpert.data.repository.AuthRepository
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.ap.pdfviewerappbookxpert.model.GoogleUser
import com.ap.pdfviewerappbookxpert.model.NetworkControl
import com.ap.pdfviewerappbookxpert.model.Resource
import com.ap.pdfviewerappbookxpert.model.ResponseState
import com.ap.pdfviewerappbookxpert.model.User
import com.google.firebase.auth.AuthCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {


    private var _authenticateUserLiveData: MutableLiveData<ResponseState<User>> = MutableLiveData()
    val authenticateUserLiveData: LiveData<ResponseState<User>> get() = _authenticateUserLiveData

    fun signInWithGoogle(googleAuthCredential: AuthCredential) {
        _authenticateUserLiveData = repository.firebaseSignInWithGoogle(googleAuthCredential)
        val user : User? = _authenticateUserLiveData.value?.data
        saveUser(user)
    }

    fun saveUser(user: User?){
        viewModelScope.launch {
            user?.let { repository.saveUser(it) }
        }
    }
}