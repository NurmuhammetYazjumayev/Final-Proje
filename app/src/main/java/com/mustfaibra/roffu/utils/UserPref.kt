package com.mustfaibra.roffu.utils

import com.mustfaibra.roffu.models.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object UserPref {
    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> get() = _user

    fun updateUser(user: User) {
        _user.value = user
    }

    fun clearUserSession() {
        _user.value = null
    }
}
