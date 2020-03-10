package com.example.profitclub.ui.browse

import BidsConsultantBidItem
import BidsConsultantPreviewClient
import RequestBidsConsultantBidItem
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.profitclub.data.bids.*
import kotlinx.coroutines.launch

class BrowseQuestionViewModel(val repository: BidsRepository) : ViewModel() {

    val bidResponse = MutableLiveData<ArrayList<BidsConsultantBid>>().apply {

    }

    val previewClientResponse = MutableLiveData<ResponseBidsConsultantPreviewClient>().apply {

    }

    val error = MutableLiveData<String>()

    val placeBid = fun(question_id: Int, deadline: String, price: Float){
        viewModelScope.launch {
            try {
                val response = repository.postBidsConsultantBid(RequestBidsConsultantBidItem(
                    BidsConsultantBidItem(question_id, deadline, price)))
                if (response.isSuccessful){
                    bidResponse.apply { value = response.body() }
                }
            } catch (e: Exception){
                error.apply { value = e.message }
            }
        }
    }

    val previewClient = fun(question_id: String){
        viewModelScope.launch {
            try {
                val response = repository.postBidsConsultantPreviewClient(BidsConsultantPreviewClient(question_id))
                if (response.isSuccessful){
                    previewClientResponse.apply { value = response.body() }
                }
            } catch (e: Exception){
                error.apply { value = e.message }
            }
        }
    }
}