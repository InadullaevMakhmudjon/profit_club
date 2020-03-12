package com.example.profitclub.ui.bids.dispute

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.profitclub.data.questions.QuestionConsultantDisputeView
import com.example.profitclub.data.questions.QuestionRepository
import kotlinx.coroutines.launch

class InArbitrationBidsViewModel(val repository: QuestionRepository) : ViewModel() {

    val data = MutableLiveData<QuestionConsultantDisputeView>().apply {
        value = null
    }

    val error = MutableLiveData<String>()

    init {
        viewModelScope.launch {
            try {
                val response = repository.getQuestionConsultantDisputeView("ru")
                if (response.isSuccessful){
                    data.apply { value = response.body() }
                }
            } catch (e: Exception){
                error.apply { value = e.message.toString() }
            }
        }
    }
}