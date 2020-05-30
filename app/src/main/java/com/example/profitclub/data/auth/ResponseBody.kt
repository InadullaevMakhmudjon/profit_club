package com.example.profitclub.data.auth

data class Token(
    val user_id: Int,
    val type: Int,
    val token: String,
    val bill: String,
    val status: Int
)

data class UserSignIn(
    val status: Int,
    val login_id: Int,
    val type: Int
)

/*
data class MailStatus(
    val status: Boolean
)*/
data class UserInfoRequest(
    val user_id: Int,
    val company_id: Int
)
