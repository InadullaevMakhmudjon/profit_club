package com.example.profitclub.data.questions

import RequestQuestionConsultantClose
import RequestQuestionConsultantEnd
import RequestQuestionConsultantPreview
import android.content.SharedPreferences

class QuestionRepository(private val retrofit: QuestionService, private val preference: SharedPreferences) {
    private var token: String? = preference.getString("token", null)

    suspend fun getConsultantQuestionView() = retrofit.getQuestionConsultantView("JWT $token")

    suspend fun postConsultantQuestionPreview(body: RequestQuestionConsultantPreview) = retrofit.postQuestionConsultantPreview("JWT $token", body)

    suspend fun postQuestionConsultantEnd(body: RequestQuestionConsultantEnd) = retrofit.postQuestionConsultantEnd("JWT $token", body)

    suspend fun postQuestionConsultantClose(body: RequestQuestionConsultantClose) = retrofit.postQuestionConsultantClose("JWT $token", body)
}