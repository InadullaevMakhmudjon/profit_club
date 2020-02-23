package com.example.profitclub.data.questions

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
)

data class QuestionConsultantView(
    val count: Int,
    val data: ArrayList<QuestionConsultantData>
)