package com.example.profitclub.ui.chats

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.profitclub.data.questions.QuestionConsultantView
import com.example.profitclub.data.questions.QuestionRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class ChatsViewModel(val repository: QuestionRepository) : ViewModel() {

    val error = MutableLiveData<String>()

    val text = MutableLiveData<String>().apply {
        value = "Total Messages: 10"
    }

    val data = MutableLiveData<QuestionConsultantView>().apply {
        value = null
    }

    init {
        viewModelScope.launch {
            try {
                val res = repository.getConsultantQuestionView()
                if(res.isSuccessful) {
                    data.apply { value = res.body() }
                }
            } catch (e: Exception) {
                error.apply { value = e.message }
            }
        }
    }
}