package com.example.profitclub.ui.questions.close

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.profitclub.data.questions.QuestionConsultantClosedData
import com.example.profitclub.data.questions.QuestionRepository
import com.example.profitclub.data.questions.ResponseGeneric
import kotlinx.coroutines.launch

class CompletedQuestionsViewModel(val repository: QuestionRepository) : ViewModel() {
    val data = MutableLiveData<ResponseGeneric<QuestionConsultantClosedData>>().apply {
        value = null
    }

    val error = MutableLiveData<String>()

    init {
        viewModelScope.launch {
            try {
                val response = repository.getClientQuestionClosedView(5)
                if (response.isSuccessful){
                    data.apply { value = response.body() }
                }
            } catch (e: Exception){
                error.apply { value = e.message.toString() }
            }
        }
    }
}