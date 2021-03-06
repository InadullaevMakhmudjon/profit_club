package com.example.profitclub.ui.chats

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.profitclub.data.questions.QuestionConsultantData
import com.example.profitclub.data.questions.QuestionConsultantView
import com.example.profitclub.data.bids.BidsRepository
import com.example.profitclub.data.questions.QuestionRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class ChatsViewModel(val repository: QuestionRepository) : ViewModel() {

    val error = MutableLiveData<String>()

    val data = MutableLiveData<QuestionConsultantView>().apply {
        value = null
    }

    val dataClient = MutableLiveData<QuestionConsultantView>().apply {
        value = null
    }

    init {
        viewModelScope.launch {
            try {
                val res = repository.getConsultantQuestionView(1)
                if(res.isSuccessful) {
                    data.apply { value = res.body() }
                }
            } catch (e: Exception) {
                error.apply { value = e.message }
            }
        }

        viewModelScope.launch {
            try {
                val res = repository.getClientQuestionView(arrayOf(1, 2))
                if(res.isSuccessful) {
                    dataClient.apply { value = res.body() }
                }
            } catch (e: Exception) {
                error.apply { value = e.message }
            }
        }
    }
}