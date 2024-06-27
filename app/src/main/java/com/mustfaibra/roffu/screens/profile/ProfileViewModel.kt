package com.mustfaibra.roffu.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mustfaibra.roffu.repositories.UserRepository
import com.mustfaibra.roffu.utils.UserPref
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    fun logout(onLogoutComplete: () -> Unit) {
        viewModelScope.launch {
            // Clear the user session or token
            UserPref.clearUserSession()
            // Execute any additional logout logic if needed
            onLogoutComplete()
        }
    }
}
