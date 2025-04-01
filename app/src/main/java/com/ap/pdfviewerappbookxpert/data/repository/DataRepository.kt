package com.ap.pdfviewerappbookxpert.data.repository


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    fun signIn(idToken: String) = viewModelScope.launch {
        repository.signInWithGoogle(idToken)
    }
}