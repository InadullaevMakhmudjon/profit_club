package com.example.profitclub.data.auth

import android.content.SharedPreferences

class AuthRepository(private val retrofit: AuthService, private val preference: SharedPreferences) {
    private var token_: String? = preference.getString("token", null)
    // Global token value
    var token: String? = null

    // Global token value
    var userId: Int? = null

    // Global role value
    var role: Int? = null

    // Is user logged in to check.
    val isLoggedIn: Boolean
        get() = token != null

    // Login Id
    var loginId: Int? = null

    // Registering role
    var registeringRole: Int? = null

    // Login user method
    suspend fun login(email: String, password: String) = retrofit.getToken(PostAuthBody(email, password))

    // Logout user method
    fun logout() {
        val editor = preference.edit()
        editor.putString("token", null)
        editor.putInt("role", 0)
        token = null
        role = null
        editor.apply()
    }

    // Register -------->
    suspend fun signIn(email: String, password: String, password_repeat: String, type: Int)
    = retrofit.register(PostRegisterBody(email, password, password_repeat, type))

    // Set login_id and type into storage ------->
    fun registeringUser(user: UserSignIn?){
        if(user!= null){
            val editor = preference.edit()
            editor.putInt("login_id", user.login_id)
            editor.putInt("role", user.type)
            loginId = user.login_id
            registeringRole = user.type
            editor.apply()
        }
    }

    // Mail confirm --------->
    suspend fun mailVerify(emailToken: String) = retrofit.mailVerify(MailVerifyBody(emailToken))

    // Set user to local storage
    fun setUserToken(userToken: Token?) {
        if(userToken != null) {
            val editor = preference.edit()
            editor.putString("token", userToken.token)
            editor.putInt("role", userToken.type)
            editor.putInt("user_id", userToken.user_id)
            editor.putString("bill", userToken.bill)
            editor.putString("lname", userToken.lname)
            editor.putString("media_url", userToken.media_url)
            token = userToken.token
            role = userToken.type
            userId = userToken.user_id
            editor.apply()
        }
    }

    // Initial token value
    init {
        token = preference.getString("token", null)
        role = preference.getInt("role", 0)
        userId = preference.getInt("user_id", 0)
    }

    suspend fun changePassword(body: ChangePasswordBody) = retrofit.changePassword("JWT $token", body)

    suspend fun resetPasswordStep1(body: ResetPasswordStep1Body) = retrofit.resetPasswordStep1(body)

    suspend fun resetPasswordStep2(body: ResetPasswordStep2Body) = retrofit.resetPasswordStep2(body)

    suspend fun resetPasswordStep3(body: ResetPasswordStep3Body) = retrofit.resetPasswordStep3(body)
}