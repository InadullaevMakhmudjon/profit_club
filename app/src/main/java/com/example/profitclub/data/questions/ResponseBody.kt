package com.example.profitclub.data.questions

data class QuestionConsultantData(
    val question_id: String,
    val client_id: String,
    val consultant_id: String,
    val title: String,
    val description: String,
    val categories: String,
    val question_date: String,
    val day_deadline: String,
    val hour_deadline: String,
    val price: String,
    val answer_end_description: String,
    val answer_end_date: String,
    val status: String
)

data class QuestionConsultantView(
    val count: Int,
    val data: ArrayList<QuestionConsultantData>
)