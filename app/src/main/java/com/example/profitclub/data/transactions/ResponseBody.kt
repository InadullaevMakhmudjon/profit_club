package com.example.profitclub.data.transactions

import kotlin.collections.ArrayList

data class TransactionResponseBody(
    val trans_id: Int,
    val v_type: String,
    val type: Int,
    val amount: Float,
    val trans_date: String,
    val name: String,
    val info: Info,
    val createdate: String
)

data class Info(
    val q_id: String,
    val title: String
)

data class PenaltyResponseBody(
    val question_tile: String,
    val ptype_id: Int,
    val penalty_value: Float,
    val penalty_status: Int,
    val penalty_date: String,
    val createdate: String
)

data class ResponseGeneric<GenericType>(
    val count: Int,
    val data: ArrayList<GenericType>
)
