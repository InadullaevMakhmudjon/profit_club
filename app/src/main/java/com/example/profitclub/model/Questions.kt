package com.example.profitclub.model

data class Questions(
    val question: String,
    val num_bids: Int,
    val num_saw: Int,
    val callBack: ((String) -> Unit)
){
    operator fun get(position: Int): Questions? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}