package com.example.profitclub.data.questions

import GenericRequest
import RequestQuestionConsultantCloseItem
import RequestQuestionConsultantEndItem
import RequestQuestionConsultantPreview
import RequestQuestionDisputeCloseItem
import RequestQuestionDisputeOpenItem
import android.content.SharedPreferences

class QuestionRepository(private val retrofit: QuestionService, private val preference: SharedPreferences) {
    private var token: String? = preference.getString("token", null)

    suspend fun postQuestionDisputeOpen(body: RequestQuestionDisputeOpenItem) = retrofit.postQuestionDisputeOpen("JWT $token", GenericRequest(body))

    suspend fun postQuestionDisputeClose(body: RequestQuestionDisputeCloseItem) = retrofit.postQuestionDisputeClose("JWT $token", GenericRequest(body))

    suspend fun getConsultantQuestionView() = retrofit.getQuestionConsultantView("JWT $token")

    suspend fun postConsultantQuestionPreview(body: RequestQuestionConsultantPreview) = retrofit.postQuestionConsultantPreview("JWT $token", body)

    suspend fun postQuestionConsultantEnd(body: RequestQuestionConsultantEndItem) = retrofit.postQuestionConsultantEnd("JWT $token", GenericRequest(body))

    suspend fun postQuestionConsultantClose(body: RequestQuestionConsultantCloseItem) = retrofit.postQuestionConsultantClose("JWT $token", GenericRequest(body))

    suspend fun getQuestionConsultantDisputeView(lang: String? = "") = retrofit.getQuestionConsultantDisputeView("JWT $token", lang)


    suspend fun getClientQuestionView() = retrofit.getQuestionClientView("JWT $token")

    suspend fun postClientQuestionPreview(body: RequestQuestionConsultantPreview) = retrofit.postQuestionClientPreview("JWT $token", body)

    suspend fun postQuestionClientEnd(body: RequestQuestionConsultantEndItem) = retrofit.postQuestionClientEnd("JWT $token", GenericRequest(body))

    suspend fun postQuestionClientClose(body: RequestQuestionConsultantCloseItem) = retrofit.postQuestionClientClose("JWT $token", GenericRequest(body))

    suspend fun getQuestionClientDisputeView(lang: String? = "") = retrofit.getQuestionClientDisputeView("JWT $token", lang)
}