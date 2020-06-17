package com.example.profitclub.model

class UserDetail(
    val id: Long,
    var requestId: String,
    var paymentId: Long?,
    var paid: Boolean
)