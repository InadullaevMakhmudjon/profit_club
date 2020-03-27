package com.example.profitclub.ui.questions.dispute

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.profitclub.data.questions.QuestionConsultantDisputeView
import com.example.profitclub.data.questions.QuestionRepository
import kotlinx.coroutines.launch

class InArbitrationQuestionsViewModel(val repository: QuestionRepository) : ViewModel() {

    val data = MutableLiveData<QuestionConsultantDisputeView>().apply {
        value = null
    }

    val error = MutableLiveData<String>()

    val getData = fun() {
        viewModelScope.launch {
            try {
                val response = repository.getQuestionClientDisputeView("ru")
                if (response.isSuccessful){
                    data.apply { value = response.body() }
                }
            } catch (e: Exception){
                error.apply { value = e.message.toString() }
            }
        }
    }
}