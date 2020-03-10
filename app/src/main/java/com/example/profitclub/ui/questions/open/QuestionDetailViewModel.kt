package com.example.profitclub.ui.questions.open

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.profitclub.data.bids.BidsRepository
import com.example.profitclub.data.bids.ClientClickView
import com.example.profitclub.data.bids.ConsultantBidsData
import com.example.profitclub.data.bids.ResponseGeneric
import kotlinx.coroutines.launch

class QuestionDetailViewModel(val repository: BidsRepository) : ViewModel() {

    val data = MutableLiveData<ResponseGeneric<ClientClickView>>().apply {
        value = null
    }

    val error = MutableLiveData<String>()

    val previewBids = fun(question_id: Int){
        viewModelScope.launch {
            try {
                val response = repository.getBidsClientClickView(question_id)
                if (response.isSuccessful){
                    data.apply { value = response.body() }
                }
            } catch (e: Exception){
                error.apply{ value = error.value.toString() }
            }
        }
    }
}