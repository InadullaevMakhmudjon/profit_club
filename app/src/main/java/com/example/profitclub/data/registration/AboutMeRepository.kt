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

    // UserGetInfo
    suspend fun getUserInfo() = retrofit.userGetInfo("JWT $token")

    // Delete Photo
    suspend fun deletePhoto(body: DeleteUploadPhotoBody) = retrofit.deletePhoto("JWT $token", body)

    // Upload Photo
    suspend fun uploadPhoto(body: DeleteUploadPhotoBody) = retrofit.uploadPhoto("JWT $token", body)

    // Save
    suspend fun save(body: PostUserInfoBody) = retrofit.save("JWT $token", body)

    suspend fun uploadDemo(file: MultipartBody.Part, id: RequestBody, type: RequestBody) = retrofit.upload(file, id, type)

    // User Staff
    suspend fun userStaff(lang: String? = "") = retrofit.getUserStaff("JWT $token", lang)
}