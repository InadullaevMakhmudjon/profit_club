package com.example.profitclub.data.registration

import com.example.profitclub.data.*
import com.example.profitclub.data.bids.DataBid
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface RegistrationService {
    @POST(GET_ALL_FILTERED)
    suspend fun getRegions(@Body body:PostRegionBody = PostRegionBody()): Response<ArrayList<Data>>

    @POST(GET_ALL_FILTERED)
    suspend fun getCities(@Body body: PostCityBody): Response<ArrayList<Data>>

    @POST(GET_ALL_FILTERED)
    suspend fun getCategories(@Body body: PostCategoryBody = PostCategoryBody()): Response<ArrayList<DataBid>>

    @POST(USER_INFO)
    suspend fun getUserInfoClientIndividual(@Body body: UserInfoBodyClientIndividual): Response<UserInfoResponse>

    @POST(USER_INFO)
    suspend fun getUserInfoClientLegal(@Body body: UserInfoBodyClientLegal): Response<UserInfoResponse>

    @POST(USER_INFO)
    suspend fun getUserInfoConsultantIndividual(@Body body: UserInfoBodyConsultantIndividual): Response<UserInfoResponse>

    @POST(USER_INFO)
    suspend fun getUserInfoConsultantLegal(@Body body: UserInfoBodyConsultantLegal): Response<UserInfoResponse>

    @GET(GET_USER_INFO)
    suspend fun userGetInfo(@Header("authorization") auth: String?): Response<GetUserInfoBody>

    @POST(DELETE_PHOTO)
    suspend fun deletePhoto(@Header("authorization") auth: String?, @Body body: DeleteUploadPhotoBody): Response<Unit>

    @POST(UPLOAD_PHOTO)
    suspend fun uploadPhoto(@Header("authorization") auth: String?, @Body body: DeleteUploadPhotoBody): Response<UploadPhotoResponse>

    @POST(SAVE)
    suspend fun save(@Header("authorization") auth: String?, @Body body: PostUserInfoBody): Response<UploadPhotoResponse>

    @Multipart
    @POST(UPLOAD_IMAGE)
    suspend fun upload(@Part file: MultipartBody.Part, @Part("id") id: RequestBody, @Part("type") type: RequestBody): Response<Unit>

    @GET(USER_STAFF)
    suspend fun getUserStaff(@Header("authorization") auth: String?, @Query("lang") lang: String?): Response<UserStaffResponse>
}