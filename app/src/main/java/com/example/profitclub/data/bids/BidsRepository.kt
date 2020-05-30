package com.example.profitclub.data.bids

import BidsClientUpdate
import BidsConsultantPreviewClient
import RequestBidsClientAdd
import RequestBidsConsultantBidItem
import android.content.SharedPreferences

class BidsRepository(private val retrofit: BidsService, private val preference: SharedPreferences) {
    private var token: String? = preference.getString("token", null)

    // Consultant
    suspend fun getConsultantBidsView(lang: String? = "") = retrofit.getBidsConsultantView("JWT $token", lang)
    suspend fun getBidsConsultantClickView(lang: String?) = retrofit.getBidsConsultantClickView("JWT $token", lang)
    suspend fun postBidsConsultantPreviewClient(body: BidsConsultantPreviewClient) = retrofit.postBidsConsultantPreviewClient("JWT $token", body)
    suspend fun postBidsConsultantBid(body: RequestBidsConsultantBidItem) = retrofit.postBidsConsultantBid("JWT $token", body)

    // Client
    suspend fun getBidsClientView(lang: String?) = retrofit.getBidsClientView("JWT $token", lang)
    suspend fun getBidsClientClickView(question_id: Int?) = retrofit.getBidsClientClickView("JWT $token", question_id)
    suspend fun postBidsClientUpdate(body: BidsClientUpdate) = retrofit.postBidsClientUpdate("JWT $token", body)
    suspend fun postBidsClientAdd(body: RequestBidsClientAdd) = retrofit.postBidsClientAdd("JWT $token", body)


    // All categories
    suspend fun getCategories() = retrofit.getCategories()

    // User Rating
    suspend fun getUserRating(user_id: Int?) = retrofit.getUserRating("JWT $token", user_id)

}