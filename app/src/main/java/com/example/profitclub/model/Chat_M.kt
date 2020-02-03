package com.example.profitclub.model

data class Chat_M(
    val description: String,
    val questionId: String,
    val clientId: String,
    val consultantId: String,
    val status: String
) {
    operator fun get(position: Int): Chat_M? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}