package com.example.profitclub.data.auth

import com.example.profitclub.data.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthService {
    @POST(AUTH_POST)
    suspend fun getToken(@Body req: PostAuthBody): Response<Token>

    @POST(REGISTER)
    suspend fun register(@Body req: PostRegisterBody): Response<UserSignIn>

    @POST(MAIL_VERIFY)
    suspend fun mailVerify(@Body req: MailVerifyBody ): Response<Boolean>

    @POST(CHANGE_PASSWORD)
    suspend fun changePassword(@Header("authorization") auth: String?, @Body body: ChangePasswordBody): Response<ChangePasswordStatus>

    @POST(RESET_PASSWORD)
    suspend fun resetPasswordStep1(@Body body: ResetPasswordStep1Body): Response<ResetPasswordStep1>

    @POST(RESET_PASSWORD)
    suspend fun resetPasswordStep2(@Body body: ResetPasswordStep2Body): Response<ResetPasswordStep1>

    @POST(RESET_PASSWORD)
    suspend fun resetPasswordStep3(@Body body: ResetPasswordStep3Body): Response<ResetPasswordStep1>
}