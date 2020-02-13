package com.example.profitclub.data.questions

import com.example.profitclub.data.QUESTIONCONSULTANTVIEW
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface QuestionService {
    @GET(QUESTIONCONSULTANTVIEW)
    fun getQuestionConsultantView(@Header("authorization") auth: String?): Response<QuestionConsultantView>
}