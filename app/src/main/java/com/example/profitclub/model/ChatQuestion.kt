package com.example.profitclub.model

data class ChatQuestion(
    val description: String,
    val questionId: String,
    val status: String
) {
    operator fun get(position: Int): ChatQuestion? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}