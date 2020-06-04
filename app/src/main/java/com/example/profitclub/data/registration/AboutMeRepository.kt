package com.example.profitclub.data.registration

import android.content.SharedPreferences
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

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
    suspend fun save(req: PostUserInfoBody): Response<UploadPhotoResponse> {
        val body = HashMap<String, PostUserInfoBody>()
        body["data"] = req
        req.city_id = 3
        return retrofit.save("JWT $token", body)
    }

    suspend fun uploadDemo(file: MultipartBody.Part, id: RequestBody, type: RequestBody) = retrofit.upload(file, id, type)

    // Get User Staff
    suspend fun userStaff(lang: String? = "") = retrofit.getUserStaff("JWT $token", lang)

    // Post User Staff
    suspend fun postUserStaff(body: PostUserStaffInfoBody) = retrofit.postUserStaff("JWT $token", body)

    // Post User Staff Edit
    suspend fun userStaffEdit(body: PostUserStaffInfoBody) = retrofit.postUserStaffEdit("JWT $token", body)
}