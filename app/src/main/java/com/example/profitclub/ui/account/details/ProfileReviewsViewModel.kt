package com.example.profitclub.ui.account.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.profitclub.data.bids.BidsRepository
import com.example.profitclub.data.bids.ResponseGeneric
import com.example.profitclub.data.bids.ResponseUserRating
import kotlinx.coroutines.launch

class ProfileReviewsViewModel(val repository: BidsRepository) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Reviews"
    }
    val text: LiveData<String> = _text

    val userRating = MutableLiveData<ResponseGeneric<ResponseUserRating>>().apply { value = null }

    val error = MutableLiveData<String>()

    val getUserRating = fun (user_id: Int?){
        viewModelScope.launch {
            try {
                val response = repository.getUserRating(user_id)
                if (response.isSuccessful){
                    userRating.apply { value = response.body() }
                }
            } catch (e:Exception){
                error.apply { value = e.message.toString() }
            }
        }
    }
}