package com.example.profitclub.data.auth

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("user/login/")
    suspend fun getToken(@Body req: PostAuthBody): Response<Token>
}