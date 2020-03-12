package com.example.profitclub.ui.questions.close

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.profitclub.data.Service
import com.example.profitclub.data.questions.QuestionRepository
import com.example.profitclub.data.questions.QuestionService

class CloseQuestionViewModelFactory(val preference: SharedPreferences): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CompletedQuestionsViewModel::class.java)) {
            return CompletedQuestionsViewModel(
                repository = QuestionRepository(
                    retrofit = Service.createService(QuestionService::class.java),
                    preference = preference
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}