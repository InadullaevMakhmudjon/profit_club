package com.example.profitclub.data.auth

import com.example.profitclub.data.AUTH_POST
import com.example.profitclub.data.MAIL_CONFIRM
import com.example.profitclub.data.MAIL_VERIFY
import com.example.profitclub.data.REGISTER
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST(AUTH_POST)
    suspend fun getToken(@Body req: PostAuthBody): Response<Token>

    @POST(REGISTER)
    suspend fun register(@Body req: PostRegisterBody): Response<UserSignIn>

    @POST(MAIL_VERIFY)
    suspend fun mailVerify(@Body req: MailVerifyBody ): Response<Boolean>

}