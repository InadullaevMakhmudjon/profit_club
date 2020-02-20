package com.example.profitclub.data.registration

import retrofit2.Retrofit
import java.sql.Timestamp

class RegistrationRepository(private val retrofit: RegistrationService){

    // All regions
    suspend fun getRegions() = retrofit.getRegions()

    // All cities
    suspend fun getCities(regionId: Int) = retrofit.getCities(PostCityBody(SetRegionId(regionId)))

    // All categories
    suspend fun getCategories() = retrofit.getCategories()

    //UserInfo
    suspend fun userInfo( login_id: Int, lname: String, fname: String, mname: String, gender_id: Int,
                         date: String, phone: String, country_id: Int, region_id: Int,
                         city_id: Int, address: String, passport_no: String, languages: IntArray,
                          categories: IntArray, about: String)
            = retrofit.getUserInfo(
        UserInfoBody(login_id, lname, fname, mname, gender_id, date, phone, country_id,
            region_id, city_id, address, passport_no, languages, categories, about))

}