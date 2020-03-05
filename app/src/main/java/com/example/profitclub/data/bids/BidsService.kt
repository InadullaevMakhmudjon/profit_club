package com.example.profitclub.data.bids

import com.example.profitclub.data.BIDS_CONSULTANT_VIEW
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface BidsService {

    // Consultant
    @GET(BIDS_CONSULTANT_VIEW)
    suspend fun getBidsConsultantView(@Header("authorization") auth: String?): Response<ResponseBidsConsultant> }