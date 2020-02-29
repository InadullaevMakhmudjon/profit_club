package com.example.profitclub.data.questions

import java.io.Serializable

data class QuestionConsultantData(
    val question_id: Int,
    val client_id: Int,
    val consultant_id: Int,
    val title: String,
    val description: String,
    val qlang: String,
    val categories: ArrayList<String>,
    val question_date: String,
    val day: Int,
    val hour: Int,
    val dayhour: String,
    val price: String,
    val answer_end_description: String,
    val answer_end_date: String,
    val status: Int,
    val createdate: String
) : Serializable

data class QuestionConsultantView(
    val count: Int,
    val data: ArrayList<QuestionConsultantData>
)

data class Message(
    val user: String,
    val avatar: String,
    val to: String,
    val iduser: Int,
    val idother: Int,
    val content: String,
    val date: String
)

data class ResponseQuestionConsultantPreview(
    val question_id: Int,
    val client_id: Int,
    val client_fullname: String,
    val client_rate: Float,
    val consultant_id: Int,
    val consultant_fullname: String,
    val consultant_rate: Float,
    val title: String,
    val client_avatar: String,
    val consultant_avatar: String,
    val description: String,
    val question_date: String,
    val day_deadline: Int,
    val hour_deadline: Int,
    val price: Float,
    val categories: ArrayList<String>,
    // val answer_end_description: String -> null on response
    // val answer_end_date: String -> null on response
    val messages: ArrayList<Message>,
    // val client_files: String, -> null on response
    // val consultant_files: String, -> null on response
    val status: Int
)

data class ResponseQuestionConsultantEnd(
    val question_consultant_end: Int
)

data class ResponseQuestionConsultantClose(
    val question_consultant_end: Int
)