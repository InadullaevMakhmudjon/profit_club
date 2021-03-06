package com.example.profitclub.data.questions

import GenericRequest
import RequestQuestionConsultantCloseItem
import RequestQuestionConsultantEndItem
import RequestQuestionConsultantPreview
import RequestQuestionDisputeCloseItem
import RequestQuestionDisputeOpenItem
import RequestQuestionMessage
import android.content.SharedPreferences
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

class QuestionRepository(private val retrofit: QuestionService, private val preference: SharedPreferences) {
    private var token: String? = preference.getString("token", null)
    private var _userId: Int = preference.getInt("user_id", 0)

    val userId: Int get() = _userId

    suspend fun postQuestionSendMessage(body: RequestQuestionMessage) = retrofit.postQuestionSendMessage("JWT $token", GenericRequest(body))

    suspend fun postQuestionDisputeOpen(body: RequestQuestionDisputeOpenItem) = retrofit.postQuestionDisputeOpen("JWT $token", GenericRequest(body))

    suspend fun postQuestionDisputeClose(body: RequestQuestionDisputeCloseItem) = retrofit.postQuestionDisputeClose("JWT $token", GenericRequest(body))

    suspend fun getConsultantQuestionView(status: Int) = retrofit.getQuestionConsultantView("JWT $token", status)

    suspend fun getConsultantQuestionApproveView(status: Int) = retrofit.getQuestionConsultantApproveView("JWT $token", status)

    suspend fun getConsultantQuestionCancelledView(status: Int) = retrofit.getQuestionConsultantCancelledView("JWT $token", status)

    suspend fun getConsultantQuestionClosedView(status: Int) = retrofit.getQuestionConsultantClosedView("JWT $token", status)

    suspend fun postConsultantQuestionPreview(body: RequestQuestionConsultantPreview) = retrofit.postQuestionConsultantPreview("JWT $token", body)

    suspend fun postQuestionConsultantEnd(body: RequestQuestionConsultantEndItem) = retrofit.postQuestionConsultantEnd("JWT $token", GenericRequest(body))

    suspend fun postQuestionConsultantClose(body: RequestQuestionConsultantCloseItem) = retrofit.postQuestionConsultantClose("JWT $token", GenericRequest(body))

    suspend fun getQuestionConsultantDisputeView(lang: String? = "") = retrofit.getQuestionConsultantDisputeView("JWT $token", lang)


    suspend fun getClientQuestionView(status: Array<Int>) = retrofit.getQuestionClientView("JWT $token", status)

    suspend fun getClientQuestionApproveView(status: Int) = retrofit.getQuestionClientApproveView("JWT $token", status)

    suspend fun getClientQuestionCancelledView(status: Int) = retrofit.getQuestionClientCancelledView("JWT $token", status)

    suspend fun getClientQuestionClosedView(status: Int) = retrofit.getQuestionClientClosedView("JWT $token", status)

    suspend fun postClientQuestionPreview(body: RequestQuestionConsultantPreview) = retrofit.postQuestionClientPreview("JWT $token", body)

    suspend fun postQuestionClientEnd(body: RequestQuestionConsultantEndItem) = retrofit.postQuestionClientEnd("JWT $token", GenericRequest(body))

    suspend fun postQuestionClientClose(body: RequestQuestionConsultantCloseItem) = retrofit.postQuestionClientClose("JWT $token", GenericRequest(body))

    suspend fun getQuestionClientDisputeView(lang: String? = "") = retrofit.getQuestionClientDisputeView("JWT $token", lang)

    suspend fun uploadFileChat(file: MultipartBody.Part, questionId: RequestBody): Response<Unit> {
        return retrofit.postUploadFile("JWT $token", file, questionId)
    }
}