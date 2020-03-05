package com.example.profitclub.ui.browse

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.profitclub.data.bids.BidsRepository
import com.example.profitclub.data.bids.ConsultantBidsData
import com.example.profitclub.data.bids.ResponseGeneric
import kotlinx.coroutines.launch

class BrowseViewModel(val repository: BidsRepository) : ViewModel() {

    val bidsConsView = MutableLiveData<ResponseGeneric<ConsultantBidsData>>().apply {
        value = null
    }

    val error = MutableLiveData<String>()

    init {
        viewModelScope.launch {
            try {
                val response = repository.getConsultantBidsView()
                if (response.isSuccessful){
                    bidsConsView.apply { value = response.body() }
                }
            } catch ( e: Exception){
                error.apply { value = e.message }
            }
        }
    }
}