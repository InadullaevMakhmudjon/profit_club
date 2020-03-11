package com.example.profitclub.ui.chats.chat1

import RequestQuestionConsultantPreview
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.profitclub.data.questions.Message
import com.example.profitclub.data.questions.QuestionRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class ChatsView1Model(repository: QuestionRepository) : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Total Messages: 10"
    }
    val text: LiveData<String> = _text

    val error = MutableLiveData<String>()

    val userId = repository.userId

    val messages = MutableLiveData<ArrayList<Message>>()

    val getData = fun(questionId: Int) {
        viewModelScope.launch {
            try {
                val response = repository.postConsultantQuestionPreview(RequestQuestionConsultantPreview(questionId, "ru"))
                if(response.isSuccessful) {
                    messages.apply { value = response.body()?.messages }
                }
            } catch (e: Exception) {
                error.apply { value = e.message.toString() }
            }
        }
    }
}