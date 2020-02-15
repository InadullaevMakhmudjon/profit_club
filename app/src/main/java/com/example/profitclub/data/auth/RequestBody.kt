package com.example.profitclub.data.auth

import com.google.gson.annotations.Expose
import java.sql.Timestamp
import java.util.*
import kotlin.collections.ArrayList

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

data class UserInfoBody(
    val login_id: Int,
    val lname: String,
    val fname: String,
    val mname: String,
    val gender_id: Int,
    val date: Timestamp,
    val phone: String,
    val country_id: Int,
    val region_id: Int,
    val city_id: Int,
    val address: String,
    val passport_no: String,
    val languages: Array<Int>,
    val categories: Array<Int>,
    val about: String
)

data class CompanyInfoBody(
    val name: String,
    val phone: String,
    val country_id: Int,
    val region_id: Int,
    val city_id: Int,
    val address: String
)