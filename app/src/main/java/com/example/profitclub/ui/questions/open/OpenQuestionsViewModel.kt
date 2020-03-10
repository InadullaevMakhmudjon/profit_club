package com.example.profitclub.ui.questions.open

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.profitclub.data.bids.BidsRepository
import com.example.profitclub.data.bids.ConsultantBidsData
import com.example.profitclub.data.bids.ResponseGeneric
import kotlinx.coroutines.launch

class OpenQuestionsViewModel(val repository: BidsRepository) : ViewModel() {

    val data = MutableLiveData<ResponseGeneric<ConsultantBidsData>>().apply {
        value = null
    }

    val error = MutableLiveData<String>()

    init {
        viewModelScope.launch {
            try {
                val response = repository.getBidsClientView("ru")
                if (response.isSuccessful){
                    data.apply { value = response.body() }
                }
            } catch (e: Exception){
                error.apply { value = e.message.toString() }
            }
        }
    }

}