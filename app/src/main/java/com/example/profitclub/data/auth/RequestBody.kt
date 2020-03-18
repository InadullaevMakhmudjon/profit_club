package com.example.profitclub.data.auth

data class PostAuthBody(
    val email: String,
    val password: String
)

data class PostRegisterBody(
    val email: String,
    val password: String,
    val password_repeat: String,
    val type: Int
)

data class MailVerifyBody(
    val token: String
)
