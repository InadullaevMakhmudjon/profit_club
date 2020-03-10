package com.example.profitclub.ui.bids

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.profitclub.data.Service
import com.example.profitclub.data.bids.BidsRepository
import com.example.profitclub.data.bids.BidsService
import com.example.profitclub.data.questions.QuestionRepository
import com.example.profitclub.data.questions.QuestionService

class BidsArbitrationViewModelFactory(val preference: SharedPreferences): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(InArbitrationBidsViewModel::class.java)) {
            return InArbitrationBidsViewModel(
                repository = QuestionRepository(
                    retrofit = Service.createService(QuestionService::class.java),
                    preference = preference
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}