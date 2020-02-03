package com.example.profitclub.ui.account.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileReviewsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Reviews"
    }
    val text: LiveData<String> = _text
}