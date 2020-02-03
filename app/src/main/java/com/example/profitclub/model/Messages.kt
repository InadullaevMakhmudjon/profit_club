package com.example.profitclub.model

data class Messages(
    val message: String,
    val time: String,
    val role: Int
) {
    operator fun get(position: Int): Messages? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}