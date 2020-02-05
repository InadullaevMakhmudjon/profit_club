package com.example.profitclub.data.auth

import com.example.profitclub.data.AUTH_POST
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST(AUTH_POST)
    suspend fun getToken(@Body req: PostAuthBody): Response<Token>
}