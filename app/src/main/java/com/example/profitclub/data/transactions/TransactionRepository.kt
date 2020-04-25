package com.example.profitclub.data.transactions

import android.content.SharedPreferences

class TransactionRepository(private val retrofit: TransactionService, private val preference: SharedPreferences){
    private var token: String? = preference.getString("token", null)

    suspend fun getTransaction() = retrofit.getTransactions("JWT $token")

    suspend fun getPenalty() = retrofit.getPenalty("JWT $token")

}