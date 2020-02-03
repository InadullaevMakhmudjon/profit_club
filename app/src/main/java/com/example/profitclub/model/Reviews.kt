package com.example.profitclub.model

data class Reviews(
    val description: String,
    val rating: Double,
    val name: String
) {
    operator fun get(position: Int): Reviews? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}