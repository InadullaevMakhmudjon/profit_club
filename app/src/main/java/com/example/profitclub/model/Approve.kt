package com.example.profitclub.model

data class Approve(
    val name: String,
    val category: String,
    val consultantId: String,
    val status: String
) {
    operator fun get(position: Int): Approve? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}