package com.example.profitclub.data.questions

import java.util.ArrayList
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
    val iduser: Int, // Sender
    val idother: Int, // You
    val content: String, // message
    val date: String // time
)

data class File(
    val file_id: Int,
    val question_id: Int,
    val user_id: Int,
    val original_name: String,
    val url: String,
    val createdate: String
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
    val answer_end_description: String = "",
    val answer_end_date: String = "",
    val messages: ArrayList<Message>,
    val client_files: ArrayList<File>,
    val consultant_files: ArrayList<File>,
    val status: Int
)

data class ResponseQuestionConsultantEnd(
    val question_consultant_end: Int
)

data class ResponseQuestionConsultantClose(
    val question_consultant_end: Int
)

data class QuestionConsultantDisputeData(
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
    val price: Float,
    val answer_dispute: String,
    val true_user_fullname: String,
    val status: Int,
    val createdate: String
): Serializable

data class QuestionConsultantDisputeView(
    val count: Int,
    val data: ArrayList<QuestionConsultantDisputeData>
)

data class QuestionConsultantClosedData(
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

data class QuestionConsultantCancelledData(
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

data class QuestionConsultantApproveData(
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

data class ResponseGeneric<GenericType>(
    val count: Int,
    val data: ArrayList<GenericType>
)
data class MessageListener(
    val data: Message
)
