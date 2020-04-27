package com.example.profitclub.data.registration

data class Data(
    val id: Int,
    val name:String
)

data class UserInfoResponse(
    val user_id: Int,
    val company_id: Int
)

data class GetUserInfoBody(
    val user_id: Int,
    val media_url: String,
    val lname: String,
    val fname: String,
    val mname: String,
    val gender_id: Int,
    val bdate: String,
    val phone: String,
    val country_id: Int,
    val region_id: Int,
    val city_id: Int,
    val address: String,
    val passport_no: String,
    val languages: IntArray,
    val categories: IntArray,
    val about: String,
    val info: Info
)

data class Info(
    val about: String,
    val languages: IntArray,
    val categories: IntArray,
    val passport_no: String
)

data class UploadPhotoResponse(
    val status: Int
)