package com.example.profitclub.ui.questions

import BidsClientAddItem
import RequestBidsClientAdd
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.profitclub.data.bids.*
import kotlinx.coroutines.launch

class QuestionCreationViewModel(val repository: BidsRepository) : ViewModel() {

    val data = MutableLiveData<String>()

    val categories = MutableLiveData<ArrayList<DataBid>>().apply { value = null }

    val error = MutableLiveData<String>()

    val postQuestion = fun(client_id: Int, categories: ArrayList<Int>, title: String, description: String, language_id: Int, deadline: String) {
        viewModelScope.launch {
            try {
                val response = repository.postBidsClientAdd(RequestBidsClientAdd(BidsClientAddItem(client_id, categories, title, description, language_id, deadline)))
                if (response.code() == 200){
                    data.apply { value = "OK" }
                }
            } catch (e: Exception){
                error.apply { value = e.message.toString() }
            }
        }
    }

    init {
        viewModelScope.launch {
            try {
                val response = repository.getCategories()
                if (response.isSuccessful){
                    categories.apply { value = response.body() }
                }
            } catch (e: Exception){
                error.apply { value = e.message.toString() }
            }
        }
    }

}