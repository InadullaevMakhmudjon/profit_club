package com.example.profitclub.data.bids


import RequestQuestionConsultantClose
import RequestQuestionConsultantEnd
import RequestQuestionConsultantPreview
import ResponseBidsConsultant
import com.example.profitclub.data.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface BidsService {

    // Consultant
    @GET(BIDS_CONSULTANT_VIEW)
    suspend fun getBidsConsultantView(@Header("authorization") auth: String?): Response<ResponseBidsConsultant> }