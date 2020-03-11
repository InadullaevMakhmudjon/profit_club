package com.example.profitclub.data.questions

import GenericRequest
import RequestQuestionConsultantCloseItem
import RequestQuestionConsultantEndItem
import RequestQuestionConsultantPreview
import RequestQuestionDisputeCloseItem
import RequestQuestionDisputeOpenItem
import com.example.profitclub.data.*
import retrofit2.Response
import retrofit2.http.*
import java.util.ArrayList

interface QuestionService {

    @POST(QUESTION_DISPUTE_OPEN)
    suspend fun postQuestionDisputeOpen(@Header("authorization") auth: String?, @Body body: GenericRequest<RequestQuestionDisputeOpenItem>): Response<Unit>

    @POST(QUESTION_DISPUTE_CLOSE)
    suspend fun postQuestionDisputeClose(@Header("authorization") auth: String?, @Body body: GenericRequest<RequestQuestionDisputeCloseItem>): Response<Unit>

    // Consultant
    @GET(QUESTION_CONSULTANT_VIEW)
    suspend fun getQuestionConsultantView(@Header("authorization") auth: String?, @Query("status") status: Int): Response<QuestionConsultantView>

    @POST(QUESTION_CONSULTANT_PREVIEW)
    suspend fun postQuestionConsultantPreview(@Header("authorization") auth: String?, @Body body: RequestQuestionConsultantPreview): Response<ResponseQuestionConsultantPreview>

    @POST(QUESTION_CONSULTANT_END)
    suspend fun postQuestionConsultantEnd(@Header("authorization") auth: String?, @Body body: GenericRequest<RequestQuestionConsultantEndItem>): Response<ResponseQuestionConsultantEnd>

    @POST(QUESTION_CONSULTANT_CLOSE)
    suspend fun postQuestionConsultantClose(@Header("authorization") auth: String?, @Body body: GenericRequest<RequestQuestionConsultantCloseItem>): Response<ArrayList<ResponseQuestionConsultantClose>>

    @GET(QUESTION_CONSULTANT_DISPUTE_VIEW)
    suspend fun getQuestionConsultantDisputeView(@Header("authorization") auth: String?, @Query("lang") lang: String?): Response<QuestionConsultantDisputeView>

    // Client
    @GET(QUESTION_CLIENT_VIEW)
    suspend fun getQuestionClientView(@Header("authorization") auth: String?, @Query("status") status: Int): Response<QuestionConsultantView>

    @POST(QUESTION_CLIENT_PREVIEW)
    suspend fun postQuestionClientPreview(@Header("authorization") auth: String?, @Body body: RequestQuestionConsultantPreview): Response<ArrayList<ResponseQuestionConsultantPreview>>

    @POST(QUESTION_CLIENT_END)
    suspend fun postQuestionClientEnd(@Header("authorization") auth: String?, @Body body: GenericRequest<RequestQuestionConsultantEndItem>): Response<ResponseQuestionConsultantEnd>

    @POST(QUESTION_CLIENT_CLOSE)
    suspend fun postQuestionClientClose(@Header("authorization") auth: String?, @Body body: GenericRequest<RequestQuestionConsultantCloseItem>): Response<ArrayList<ResponseQuestionConsultantClose>>
}