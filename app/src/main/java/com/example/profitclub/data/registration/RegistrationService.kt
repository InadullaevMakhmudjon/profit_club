package com.example.profitclub.data.registration

import com.example.profitclub.data.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface RegistrationService {
    @POST(GET_ALL_FILTERED)
    suspend fun getRegions(@Body body:PostRegionBody = PostRegionBody()): Response<ArrayList<Data>>

    @POST(GET_ALL_FILTERED)
    suspend fun getCities(@Body body: PostCityBody): Response<ArrayList<Data>>

    @POST(GET_ALL_FILTERED)
    suspend fun getCategories(@Body body: PostCategoryBody = PostCategoryBody()): Response<ArrayList<Data>>

    @POST(USER_INFO)
    suspend fun getUserInfo(@Body body: UserInfoBody): Response<UserInfoResponse>

    @GET(GET_USER_INFO)
    suspend fun userGetInfo(@Header("authorization") auth: String?): Response<GetUserInfoBody>

    @POST(DELETE_PHOTO)
    suspend fun deletePhoto(@Header("authorization") auth: String?, @Body body: DeleteUploadPhotoBody): Response<Unit>

    @POST(UPLOAD_PHOTO)
    suspend fun uploadPhoto(@Header("authorization") auth: String?, @Body body: DeleteUploadPhotoBody): Response<UploadPhotoResponse>

    @POST(SAVE)
    suspend fun save(@Header("authorization") auth: String?, @Body body: PostUserInfoBody): Response<UploadPhotoResponse>
}