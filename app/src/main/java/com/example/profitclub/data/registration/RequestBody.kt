package com.example.profitclub.data.registration

data class SetRegionId(
    val region_id: Int
)

data class PostCityBody(
    val sfilter: SetRegionId,
    val lang: String = "en",
    private val token: String = "45b28bfb"
)

data class PostRegionBody(
    val lang: String = "en",
    private val token: String = "3607ac33"
)

data class PostCategoryBody(
    val lang: String = "en",
    private val token: String = "249340fe"
)

data class UserInfoBody(
    val login_id: Int,
    val lname: String,
    val fname: String,
    val mname: String,
    val gender_id: Int,
    val date: String,
    val phone: String,
    val country_id: Int,
    val region_id: Int,
    val city_id: Int,
    val address: String,
    val passport_no: String,
    val languages: IntArray,
    val categories: IntArray,
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