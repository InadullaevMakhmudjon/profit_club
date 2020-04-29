package com.example.profitclub.data.registration

import android.content.SharedPreferences
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AboutMeRepository(private val retrofit: RegistrationService, private val preference: SharedPreferences){
    private var token: String? = preference.getString("token", null)
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

    // UserGetInfo
    suspend fun getUserInfo() = retrofit.userGetInfo("JWT $token")

    // Delete Photo
    suspend fun deletePhoto(body: DeleteUploadPhotoBody) = retrofit.deletePhoto("JWT $token", body)

    // Upload Photo
    suspend fun uploadPhoto(body: DeleteUploadPhotoBody) = retrofit.uploadPhoto("JWT $token", body)

    // Save
    suspend fun save(body: PostUserInfoBody) = retrofit.save("JWT $token", body)

    suspend fun uploadDemo(file: MultipartBody.Part, id: RequestBody, type: RequestBody) = retrofit.upload(file, id, type)
}