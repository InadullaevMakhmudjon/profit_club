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

data class MailConfirmBody(
    val login_id: Int,
    val lang: String
)

data class ChangePasswordBody(
    val old_password: String,
    val new_password: String,
    val password_repeat: String,
    val lang: String
)

data class CheckAuthBody(
 val email: String,
 val lang: String
)

data class ResetPasswordStep1Body(
    val user: Email,
    val step: Int,
    val lang: String
)

data class Email(
    val email: String
)

data class ResetPasswordStep2Body(
    val user: EmailHash,
    val step: Int,
    val lang: String
)

data class EmailHash(
    val email: String,
    val hash: String
)

data class ResetPasswordStep3Body(
    val user: EmailHashPassword,
    val step: Int,
    val lang: String
)

data class EmailHashPassword(
    val email: String,
    val hash: String,
    val password: String,
    val password_repeat: String
)

