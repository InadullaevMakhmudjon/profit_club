package com.example.profitclub.data.questions


import RequestQuestionConsultantClose
import RequestQuestionConsultantEnd
import RequestQuestionConsultantPreview
import com.example.profitclub.data.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface QuestionService {

    // Consultant
    @GET(QUESTION_CONSULTANT_VIEW)
    suspend fun getQuestionConsultantView(@Header("authorization") auth: String?): Response<QuestionConsultantView>

    @POST(QUESTION_CONSULTANT_PREVIEW)
    suspend fun postQuestionConsultantPreview(@Header("authorization") auth: String?, @Body body: RequestQuestionConsultantPreview): Response<ResponseQuestionConsultantPreview>

    @POST(QUESTION_CONSULTANT_END)
    suspend fun postQuestionConsultantEnd(@Header("authorization") auth: String?, @Body body: RequestQuestionConsultantEnd): Response<ResponseQuestionConsultantEnd>

    @POST(QUESTION_CONSULTANT_CLOSE)
    suspend fun postQuestionConsultantClose(@Header("authorization") auth: String?, @Body body: RequestQuestionConsultantClose): Response<ArrayList<ResponseQuestionConsultantClose>>

    // Client
    @GET(QUESTION_CLIENT_VIEW)
    suspend fun getQuestionClientView(@Header("authorization") auth: String?): Response<QuestionConsultantView>

    @POST(QUESTION_CLIENT_PREVIEW)
    suspend fun postQuestionClientPreview(@Header("authorization") auth: String?, @Body body: RequestQuestionConsultantPreview): Response<ResponseQuestionConsultantPreview>

    @POST(QUESTION_CLIENT_END)
    suspend fun postQuestionClientEnd(@Header("authorization") auth: String?, @Body body: RequestQuestionConsultantEnd): Response<ResponseQuestionConsultantEnd>

    @POST(QUESTION_CLIENT_CLOSE)
    suspend fun postQuestionClientClose(@Header("authorization") auth: String?, @Body body: RequestQuestionConsultantClose): Response<ArrayList<ResponseQuestionConsultantClose>>
}