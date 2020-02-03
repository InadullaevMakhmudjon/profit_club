package com.example.profitclub.model

data class Transaction(
    val date: String,
    val description: String,
    val status: Int
) {
    operator fun get(position: Int): Transaction? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}