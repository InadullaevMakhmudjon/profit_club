package com.example.profitclub.data.questions

import android.content.SharedPreferences

class QuestionRepository(private val retrofit: QuestionService, private val preference: SharedPreferences) {
    private var token: String? = preference.getString("token", null)

    suspend fun getConsultantQuestionView() = retrofit.getQuestionConsultantView("Bearer $token")

}