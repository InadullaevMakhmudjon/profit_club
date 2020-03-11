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
    private var _userId: Int = preference.getInt("user_id", 0)

    val userId: Int get() = _userId

    suspend fun postQuestionDisputeOpen(body: RequestQuestionDisputeOpenItem) = retrofit.postQuestionDisputeOpen("JWT $token", GenericRequest(body))

    suspend fun postQuestionDisputeClose(body: RequestQuestionDisputeCloseItem) = retrofit.postQuestionDisputeClose("JWT $token", GenericRequest(body))

    suspend fun getConsultantQuestionView(status: Int) = retrofit.getQuestionConsultantView("JWT $token", status)

    suspend fun postConsultantQuestionPreview(body: RequestQuestionConsultantPreview) = retrofit.postQuestionConsultantPreview("JWT $token", body)

    suspend fun postQuestionConsultantEnd(body: RequestQuestionConsultantEndItem) = retrofit.postQuestionConsultantEnd("JWT $token", GenericRequest(body))

    suspend fun postQuestionConsultantClose(body: RequestQuestionConsultantCloseItem) = retrofit.postQuestionConsultantClose("JWT $token", GenericRequest(body))

    suspend fun getQuestionConsultantDisputeView(lang: String? = "") = retrofit.getQuestionConsultantDisputeView("JWT $token", lang)


    suspend fun getClientQuestionView(status: Int) = retrofit.getQuestionClientView("JWT $token", status)

    suspend fun postClientQuestionPreview(body: RequestQuestionConsultantPreview) = retrofit.postQuestionClientPreview("JWT $token", body)

    suspend fun postQuestionClientEnd(body: RequestQuestionConsultantEndItem) = retrofit.postQuestionClientEnd("JWT $token", GenericRequest(body))

    suspend fun postQuestionClientClose(body: RequestQuestionConsultantCloseItem) = retrofit.postQuestionClientClose("JWT $token", GenericRequest(body))

}