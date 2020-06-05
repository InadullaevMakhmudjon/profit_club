package com.example.profitclub.data.auth

data class Token(
    val user_id: Int,
    val lname: String,
    val type: Int,
    val token: String,
    val media_url: String,
    val bill: String,
    val status: Int
)

data class UserSignIn(
    val status: Int,
    val login_id: Int,
    val type: Int
)


data class ChangePasswordStatus(
    val status: Int
)

data class UserInfoRequest(
    val user_id: Int,
    val company_id: Int
)

data class ResetPasswordStep1(
    val status: Boolean
)

