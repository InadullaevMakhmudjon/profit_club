package com.example.profitclub.data.questions

import RequestQuestionConsultantClose
import RequestQuestionConsultantEnd
import RequestQuestionConsultantPreview
import android.content.SharedPreferences
import com.example.profitclub.data.bids.BidsService

class QuestionRepository(private val retrofit: BidsService, private val preference: SharedPreferences) {
    private var token: String? = preference.getString("token", null)

    suspend fun getConsultantQuestionView() = retrofit.getQuestionConsultantView("JWT $token")

    suspend fun postConsultantQuestionPreview(body: RequestQuestionConsultantPreview) = retrofit.postQuestionConsultantPreview("JWT $token", body)

    suspend fun postQuestionConsultantEnd(body: RequestQuestionConsultantEnd) = retrofit.postQuestionConsultantEnd("JWT $token", body)

    suspend fun postQuestionConsultantClose(body: RequestQuestionConsultantClose) = retrofit.postQuestionConsultantClose("JWT $token", body)

    suspend fun getClientQuestionView() = retrofit.getQuestionClientView("JWT $token")

    suspend fun postClientQuestionPreview(body: RequestQuestionConsultantPreview) = retrofit.postQuestionClientPreview("JWT $token", body)

    suspend fun postQuestionClientEnd(body: RequestQuestionConsultantEnd) = retrofit.postQuestionClientEnd("JWT $token", body)

    suspend fun postQuestionClientClose(body: RequestQuestionConsultantClose) = retrofit.postQuestionClientClose("JWT $token", body)

}