package com.example.profitclub.data.bids

import android.content.SharedPreferences

class BidsRepository(private val retrofit: BidsService, private val preference: SharedPreferences) {
    private var token: String? = preference.getString("token", null)

    suspend fun getConsultantBidsView() = retrofit.getBidsConsultantView("JWT $token")
}