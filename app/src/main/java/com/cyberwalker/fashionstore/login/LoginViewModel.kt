package com.cyberwalker.fashionstore.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cyberwalker.fashionstore.data.source.LoggedInUser
import com.cyberwalker.fashionstore.util.LoadingState
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    val loadingState = MutableStateFlow(LoadingState.IDLE)
    private var _loggedInUser: MutableLiveData<LoggedInUser> = MutableLiveData<LoggedInUser>()
//    val loggedInUser: LiveData<LoggedInUser> get() = _loggedInUser

    fun signInWithEmailAndPassword(email: String, password: String) = viewModelScope.launch {
        try {
            loadingState.emit(LoadingState.LOADING)
            Firebase.auth.signInWithEmailAndPassword(email, password).await()
            val user = Firebase.auth.currentUser
            var user1 = LoggedInUser("", "")
            setLoggedInUser(
                user1.apply {
                    userId = user?.uid ?: "0"
                    displayName = user?.email ?: "Unknown"
                }//.copy(
//                        userId = user?.uid ?: "0"
//                        displayName = user?.email ?: "Unknown"
//                )
            )
            loadingState.emit(LoadingState.LOADED)
        } catch (e: Exception) {
            loadingState.emit(LoadingState.error(e.localizedMessage))
        }
    }


    fun signWithCredential(credential: AuthCredential) = viewModelScope.launch {
        try {
            loadingState.emit(LoadingState.LOADING)
            Firebase.auth.signInWithCredential(credential).await()
            loadingState.emit(LoadingState.LOADED)
        } catch (e: Exception) {
            loadingState.emit(LoadingState.error(e.localizedMessage))
        }
    }

   private fun setLoggedInUser(_user: LoggedInUser) {
        _loggedInUser.postValue(_user)
    }

}