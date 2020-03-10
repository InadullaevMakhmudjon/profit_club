package com.example.profitclub.ui.bids

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.profitclub.data.bids.BidsRepository
import com.example.profitclub.data.bids.ConsultantBidsClickData
import com.example.profitclub.data.bids.ResponseGeneric
import kotlinx.coroutines.launch

class OpenBidsViewModel(val repository: BidsRepository) : ViewModel() {

    val error = MutableLiveData<String>()

    val data = MutableLiveData<ResponseGeneric<ConsultantBidsClickData>>().apply {
        value = null
    }

    init {
        viewModelScope.launch {
            try {
                val res = repository.getBidsConsultantClickView("ru")
                if(res.isSuccessful) {
                    data.apply { value = res.body() }
                }
            } catch (e: Exception) {
                error.apply { value = e.message }
            }
        }
    }
}