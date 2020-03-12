package com.example.profitclub.ui.bids.approve

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.profitclub.data.questions.QuestionConsultantApproveData
import com.example.profitclub.data.questions.QuestionRepository
import com.example.profitclub.data.questions.ResponseGeneric
import kotlinx.coroutines.launch

class InApprovingBidsViewModel(val repository: QuestionRepository) : ViewModel() {

    val data = MutableLiveData<ResponseGeneric<QuestionConsultantApproveData>>().apply {
        value = null
    }

    val error = MutableLiveData<String>()

    init {
        viewModelScope.launch {
            try {
                val response = repository.getConsultantQuestionApproveView(2)
                if (response.isSuccessful){
                    data.apply { value = response.body() as ResponseGeneric<QuestionConsultantApproveData> }
                }
            } catch (e: Exception){
                error.apply { value = e.message.toString() }
            }
        }
    }
}