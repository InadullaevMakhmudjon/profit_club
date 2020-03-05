package com.example.profitclub.data.bids

import BidsClientUpdate
import BidsConsultantBid
import BidsConsultantPreviewClient
import ClientClickView
import ConsultantBidsClickData
import ConsultantBidsData
import RequestBidsClientAdd
import RequestBidsConsultantBidItem
import ResponseBidsClientUpdate
import ResponseBidsConsultantPreviewClient
import ResponseGeneric
import com.example.profitclub.data.*
import retrofit2.Response
import retrofit2.http.*

interface BidsService {

    // Consultant
    @GET(BIDS_CONSULTANT_VIEW)
    suspend fun getBidsConsultantView(@Header("authorization") auth: String?): Response<ResponseGeneric<ConsultantBidsData>>

    @GET(BIDS_CONSULTANT_CLICK_VIEW)
    suspend fun getBidsConsultantClickView(@Header("authorization") auth: String?, @Path("lang") lang: String?): Response<ResponseGeneric<ConsultantBidsClickData>>

    @POST(BIDS_CONSULTANT_PREVIEW_CLIENT)
    suspend fun postBidsConsultantPreviewClient(@Header("authorization") auth: String?, @Body body: BidsConsultantPreviewClient): Response<ResponseBidsConsultantPreviewClient>

    @POST(BIDS_CONSULTANT_BID)
    suspend fun postBidsConsultantBid(@Header("authorization") auth: String?, @Body body: RequestBidsConsultantBidItem): Response<ArrayList<BidsConsultantBid>>

    // Client
    @GET(BIDS_CLIENT_VIEW)
    suspend fun getBidsClientView(@Header("authorization") auth: String?, @Path("lang") lang: String?): Response<ResponseGeneric<ConsultantBidsData>>

    @GET(BIDS_CLIENT_CLICK_VIEW)
    suspend fun getBidsClientClickView(@Header("authorization") auth: String?, @Path("id") question_id: Int?): Response<ResponseGeneric<ClientClickView>>

    @POST(BIDS_CLIENT_UPDATE)
    suspend fun postBidsClientUpdate(@Header("authorization") auth: String?, @Body body: BidsClientUpdate): Response<ArrayList<ResponseBidsClientUpdate>>

    @POST(BIDS_CLIENT_ADD)
    suspend fun postBidsClientAdd(@Header("authorization") auth: String?, @Body body: RequestBidsClientAdd)
}