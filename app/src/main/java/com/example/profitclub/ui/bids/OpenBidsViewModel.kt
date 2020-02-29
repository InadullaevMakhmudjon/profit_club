package com.example.profitclub.ui.bids

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.profitclub.data.questions.QuestionConsultantView
import com.example.profitclub.data.questions.QuestionRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class OpenBidsViewModel(val repository: QuestionRepository) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is gallery Fragment"
    }

    val text: LiveData<String> = _text

    val error = MutableLiveData<String>()

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