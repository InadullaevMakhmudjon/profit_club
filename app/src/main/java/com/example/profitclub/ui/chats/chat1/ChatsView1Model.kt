package com.example.profitclub.ui.chats.chat1

import RequestQuestionConsultantPreview
import RequestQuestionMessage
import android.app.Application
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.profitclub.data.questions.Message
import com.example.profitclub.data.questions.QuestionRepository
import com.example.profitclub.hideKeyboard
import com.example.profitclub.utils.ObservableViewModel
import kotlinx.coroutines.launch
import java.lang.Exception

class ChatsView1Model(application: Application, private val repository: QuestionRepository) : ObservableViewModel(application) {
    private val _text = MutableLiveData<String>().apply {
        value = "Total Messages: 10"
    }
    val text: LiveData<String> = _text

    val questionId = MutableLiveData<Int>().apply { value = 0 }

    val messageText = MutableLiveData<String>()

    val error = MutableLiveData<String>()

    val userId = repository.userId

    val receiverId = MutableLiveData<Int>().apply { value = 0 }

    val messages = MutableLiveData<ArrayList<Message>>().apply { value = arrayListOf() }

    fun sendBtn(v: View) {
        if(messages.value!!.size > 0) {
            viewModelScope.launch {
                try {
                    val request = RequestQuestionMessage(
                        question_id = questionId.value!!,
                        content = messageText.value,
                        idother = receiverId.value!!)
                    repository.postQuestionSendMessage(request)
                    messageText.apply { value = "" }
                    v.hideKeyboard()
                } catch (e: Exception) {
                    error.apply { value = e.message.toString() }
                }
            }

        }
    }

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