package com.example.profitclub.model

data class Bids(
    val name: String,
    val price: String,
    val role: Int
) {
    operator fun get(position: Int): Bids? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}