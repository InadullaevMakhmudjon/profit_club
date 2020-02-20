package com.example.profitclub.data.registration

import com.example.profitclub.data.GETALLFILTERED
import com.example.profitclub.data.USER_INFO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegistrationService {
    @POST(GETALLFILTERED)
    suspend fun getRegions(@Body body:PostRegionBody = PostRegionBody()): Response<ArrayList<Data>>

    @POST(GETALLFILTERED)
    suspend fun getCities(@Body body: PostCityBody): Response<ArrayList<Data>>

    @POST(GETALLFILTERED)
    suspend fun getCategories(@Body body: PostCategoryBody = PostCategoryBody()): Response<ArrayList<Data>>

    @POST(USER_INFO)
    suspend fun getUserInfo(@Body body: UserInfoBody): Response<UserInfoResponse>
}