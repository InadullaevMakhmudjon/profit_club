package com.example.profitclub.data

import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import kotlinx.coroutines.flow.callbackFlow
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Error
import java.lang.Exception

object Service {
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <S> createService(serviceClass: Class<S>): S = retrofit.create(serviceClass)

    val socket: Socket?
        get() {
            return try {
                IO.socket("http://87.237.236.184")
            } catch (e: Exception) { null }
        }

}