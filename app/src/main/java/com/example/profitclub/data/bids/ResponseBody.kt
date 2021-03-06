package com.example.profitclub.data.bids

import java.io.Serializable
import java.util.ArrayList

data class ConsultantBidsData(
    val question_id: Int,
    val qlang: String,
    val title: String,
    val description: String,
    val categories: ArrayList<String>,
    val question_date: String,
    val status: Int,
    val client_id: Int,
    val click_count: String,
    val click_status: String,
    val createdate: String
) : Serializable

data class ConsultantBidsClickData(
    val bid_id: Int,
    val title: String,
    val description: String,
    val qlang: String,
    val categories: ArrayList<String>,
    val deadline: String,
    val price: String,
    val consultant_id: Int,
    val createdate: String
) : Serializable

data class ClientClickView(
    val bid_id: Int,
    val question_id: Int,
    val client_id: Int,
    val consultant_id: Int,
    val media_url: String,
    val fullname: String,
    val rate: Float,
    val deadline: String,
    val price: Float,
    val createdate: String
)
data class BidsConsultantBid(
    val bids_consultant_bid: String
)

data class ResponseGeneric<GenericType>(
    val count: Int,
    val data: ArrayList<GenericType>
)

data class ResponseBidsClientUpdate(
    val bids_client_update: Int
)

data class ResponseBidsConsultantPreviewClient(
    val question_id: Int,
    val client_id: Int,
    val title: String,
    val description: String,
    val media_url: String,
    val fullname: String,
    val rate: Float,
    val deadline: String
)

data class DataBid(
    val id: Int,
    val name:String
)

data class Language(
    val id: Int,
    val name: String
)

data class ResponseUserRating(
    val cdate: String,
    val description: String,
    val createdate: String
)