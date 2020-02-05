package com.example.profitclub.data.auth

import android.content.SharedPreferences

class AuthRepository(private val retrofit: AuthService, private val preference: SharedPreferences) {

    // Global token value
    var token: String? = null

    // Is user logged in to check.
    val isLoggedIn: Boolean
        get() = token != null

    // Login user method
    suspend fun login(email: String, password: String) = retrofit.getToken(PostAuthBody(email, password))

    // Logout user method
    fun logout() {
        val editor = preference.edit()
        editor.putString("token", null)
        token = null
        editor.apply()
    }

    // Set user to local storage
    fun setUserToken(userToken: String?) {
        val editor = preference.edit()
        editor.putString("token", userToken)
        token = userToken
        editor.apply()
    }

    // Initial token value
    init {
        token = preference.getString("token", null)
    }
}