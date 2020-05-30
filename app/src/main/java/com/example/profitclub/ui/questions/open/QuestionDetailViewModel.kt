package com.example.profitclub.ui.questions.open

import BidsClientUpdate
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.profitclub.data.bids.BidsRepository
import com.example.profitclub.data.bids.ClientClickView
import com.example.profitclub.data.bids.ResponseGeneric
import com.example.profitclub.data.bids.ResponseUserRating
import kotlinx.coroutines.launch

class QuestionDetailViewModel(val repository: BidsRepository) : ViewModel() {

    val data = MutableLiveData<ResponseGeneric<ClientClickView>>().apply {
        value = null
    }

    val userRating = MutableLiveData<ResponseGeneric<ResponseUserRating>>().apply { value = null }


    val error = MutableLiveData<String>()

    val postBid = fun(bidId: Int, notify: ()->Unit) {
        viewModelScope.launch {
            try {
                repository.postBidsClientUpdate(BidsClientUpdate(bidId))
                notify.invoke()
            } catch (e: Exception) {
                error.apply{ value = e.message.toString() }
            }
        }
    }

    val getUserRating = fun (user_id: Int?, notify: ()->Unit){
        viewModelScope.launch {
            try {
                val response = repository.getUserRating(user_id)
                notify.invoke()
                if (response.isSuccessful){
                    userRating.apply { value = response.body() }
                }
            } catch (e:Exception){
                error.apply { value = e.message.toString() }
            }
        }
    }

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