package com.example.profitclub.ui.chats.chat2

import RequestQuestionConsultantPreview
import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.profitclub.data.questions.File
import com.example.profitclub.data.questions.Message
import com.example.profitclub.data.questions.QuestionRepository
import com.example.profitclub.utils.ObservableViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class ChatsView2Model(application: Application, private val repository: QuestionRepository) : ObservableViewModel(application) {


    private val _text = MutableLiveData<String>().apply {
        value = "Total Messages: 10"
    }
    val text: LiveData<String> = _text

    val messageText = MutableLiveData<String>()

    val error = MutableLiveData<String>()

    val userId = repository.userId

    val refreshed = MutableLiveData<Boolean>()

    val clientFiles = MutableLiveData<ArrayList<File>>().apply { value = arrayListOf() }

    val consultantFiles = MutableLiveData<ArrayList<File>>().apply { value = arrayListOf() }

    val allFiles = MutableLiveData<ArrayList<File>>().apply { value = arrayListOf() }

    val upload = fun(file: MultipartBody.Part, qid: RequestBody, callBack: ((String)->Unit)) {
        viewModelScope.launch {
            try {
                val response = repository.uploadFileChat(file, qid)
                if(response.isSuccessful) {
                    callBack.invoke("")
                }
            } catch (e: Exception) {
                error.apply { value = e.message.toString() }
            }
        }
    }

    val getData = fun(questionId: Int) {
        viewModelScope.launch {
            try {
                val response = repository.postConsultantQuestionPreview(RequestQuestionConsultantPreview(questionId, "ru"))
                if(response.isSuccessful) {
                    refreshed.apply { value = true }
                    clientFiles.apply { value = response.body()?.client_files }
                    consultantFiles.apply { value = response.body()?.consultant_files }
                }
            } catch (e: Exception) {
                error.apply { value = e.message.toString() }
            }
        }
    }
}