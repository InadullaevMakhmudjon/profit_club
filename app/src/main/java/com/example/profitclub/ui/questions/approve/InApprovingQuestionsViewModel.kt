package com.example.profitclub.ui.questions.approve

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.profitclub.data.questions.QuestionConsultantApproveData
import com.example.profitclub.data.questions.QuestionRepository
import kotlinx.coroutines.launch

class InApprovingQuestionsViewModel(val repository: QuestionRepository) : ViewModel() {

    val data = MutableLiveData<QuestionConsultantApproveData>().apply {
        value = null
    }

    val error = MutableLiveData<String>()

    init {
        viewModelScope.launch {
            try {
                val response = repository.getClientQuestionApproveView(2)
            } catch (e: Exception){
                error.apply { value = e.message.toString() }
            }
        }
    }
}