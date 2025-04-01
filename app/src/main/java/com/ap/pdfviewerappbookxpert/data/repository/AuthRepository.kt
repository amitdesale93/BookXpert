package com.ap.pdfviewerappbookxpert.data.repository


import androidx.lifecycle.MutableLiveData
import com.ap.pdfviewerappbookxpert.data.local.UserDao
import com.ap.pdfviewerappbookxpert.model.ResponseState
import com.ap.pdfviewerappbookxpert.model.User
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val userDao: UserDao
) {
    suspend fun signInWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential).await()
    }

    // Sign in using google
    fun firebaseSignInWithGoogle(googleAuthCredential: AuthCredential): MutableLiveData<ResponseState<User>> {
        val authenticatedUserMutableLiveData: MutableLiveData<ResponseState<User>> =
            MutableLiveData()

        auth.signInWithCredential(googleAuthCredential).addOnCompleteListener { authTask ->
            if (authTask.isSuccessful) {
//                var isNewUser = authTask.result?.additionalUserInfo?.isNewUser
                val firebaseUser: FirebaseUser? = auth.currentUser
                if (firebaseUser != null) {
                    val uid = firebaseUser.uid
                    val name = firebaseUser.displayName
                    val email = firebaseUser.email
                    val user = User(id = uid, name = name, email = email)
                    authenticatedUserMutableLiveData.value = ResponseState.Success(user)

//                    saveUser(user)
                }
            } else {
                authenticatedUserMutableLiveData.value = authTask.exception?.message?.let {
                    ResponseState.Error(it)
                }

            }


        }
        return authenticatedUserMutableLiveData
    }


    suspend fun saveUser(user: User) = userDao.insertUser(user)
}