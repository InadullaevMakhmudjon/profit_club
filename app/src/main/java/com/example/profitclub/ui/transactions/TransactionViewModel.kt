package com.example.profitclub.ui.transactions

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.profitclub.data.transactions.PenaltyResponseBody
import com.example.profitclub.data.transactions.ResponseGeneric
import com.example.profitclub.data.transactions.TransactionRepository
import com.example.profitclub.data.transactions.TransactionResponseBody
import kotlinx.coroutines.launch

class TransactionViewModel(val repository: TransactionRepository) : ViewModel() {

    val transactionBody = MutableLiveData<ResponseGeneric<TransactionResponseBody>>().apply {
        value = null
    }

    val penaltyBody = MutableLiveData<ResponseGeneric<PenaltyResponseBody>>().apply {
        value = null
    }

    val error = MutableLiveData<String>()

    init {
        viewModelScope.launch {
            try {
                val response = repository.getTransaction()
                if (response.isSuccessful){
                    transactionBody.apply { value = response.body() as ResponseGeneric<TransactionResponseBody> }
                }
            } catch (e: Exception){
                error.apply { value = e.message.toString() }
            }
        }
    }

    init {
        viewModelScope.launch {
            try {
                val response = repository.getPenalty()
                if (response.isSuccessful){
                    penaltyBody.apply { value = response.body() as ResponseGeneric<PenaltyResponseBody> }
                }
            } catch (e:Exception){
                error.apply { value = e.message.toString() }
            }
        }
    }

}