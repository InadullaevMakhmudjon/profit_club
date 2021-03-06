package com.example.profitclub.ui.questions.cancel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.profitclub.data.questions.QuestionConsultantCancelledData
import com.example.profitclub.data.questions.QuestionRepository
import com.example.profitclub.data.questions.ResponseGeneric
import kotlinx.coroutines.launch

class RejectedQuestionsViewModel(val repository: QuestionRepository) : ViewModel() {

    val data = MutableLiveData<ResponseGeneric<QuestionConsultantCancelledData>>().apply {
        value = null
    }

    val error = MutableLiveData<String>()

    init {
        viewModelScope.launch {
            try {
                val response = repository.getClientQuestionCancelledView(4)
                if (response.isSuccessful){
                    data.apply { value = response.body() as ResponseGeneric<QuestionConsultantCancelledData> }
                }
            } catch (e: Exception){
                error.apply { value = e.message.toString() }
            }
        }
    }
}