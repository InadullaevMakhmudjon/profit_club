package com.example.profitclub.data.registration

import com.example.profitclub.data.GET_ALL_FILTERED
import com.example.profitclub.data.USER_INFO
import retrofit2.Response
import retrofit2.http.Body
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
}