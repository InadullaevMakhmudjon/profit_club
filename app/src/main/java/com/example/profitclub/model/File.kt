package com.example.profitclub.model

data class File(
    val name: String,
    val time: String,
    val role: Int
) {
    operator fun get(position: Int): File? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}