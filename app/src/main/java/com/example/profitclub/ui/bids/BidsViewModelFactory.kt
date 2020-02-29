package com.example.profitclub.ui.bids

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.profitclub.data.Service
import com.example.profitclub.data.questions.QuestionRepository
import com.example.profitclub.data.questions.QuestionService

class BidsViewModelFactory(val preference: SharedPreferences): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(OpenBidsViewModel::class.java)) {
            return OpenBidsViewModel(
                repository = QuestionRepository(
                    retrofit = Service.createService(QuestionService::class.java),
                    preference = preference
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}