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

data class UserInfoBodyClientIndividual(
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
    val info: InfoIndividual
)

data class InfoIndividual(
    val languages: ArrayList<Int>,
    val passport_no: String
)

data class UserInfoBodyClientLegal(
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
    val company: CompanyInfo
)

data class UserInfoBodyConsultantIndividual(
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
    val info: UserInfo
)

data class UserInfoBodyConsultantLegal(
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
    val company: CompanyInfo
)

data class UserInfo(
    val about: String,
    val languages: ArrayList<Int>,
    val categories: ArrayList<Int>,
    val passport_no: String
)

data class CompanyInfo(
    val name: String,
    val phone: String,
    val country_id: Int,
    val region_id: Int,
    val city_id: Int,
    val address: String
)

data class DeleteUploadPhotoBody(
    val user_id: Int,
    val type: Int
)

data class PostUserInfoBody(
    val user_id: Int,
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
    val info: InfoUser,
    val lang: String
)

data class InfoUser(
    val about: String,
    val languages: ArrayList<Int>,
    val categories: ArrayList<Int>,
    val passport_no: String
)

data class PostUserStaffInfoBody(
    val user_id: Int,
    val email: String,
    val password: String,
    val password_repeat: String,
    val lname: String,
    val fname: String,
    val mname: String,
    val gender_id: Int,
    val bdate: String,
    val info: InfoStuff,
    val phone: String,
    val role: String,
    val country_id: Int,
    val region_id: Int,
    val city_id: Int,
    val address: String
)
