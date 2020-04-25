package com.example.profitclub.data.transactions

import com.example.profitclub.data.PENALTY
import com.example.profitclub.data.TRANSACTION
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface TransactionService {

    @GET(TRANSACTION)
    suspend fun getTransactions(@Header("authorization") auth: String?): Response<ResponseGeneric<TransactionResponseBody>>

    @GET(PENALTY)
    suspend fun getPenalty(@Header("authorization") auth: String?): Response<ResponseGeneric<PenaltyResponseBody>>
}