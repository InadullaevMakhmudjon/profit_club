package com.example.profitclub.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Service {
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <S> createService(serviceClass: Class<S>): S = retrofit.create(serviceClass)
}