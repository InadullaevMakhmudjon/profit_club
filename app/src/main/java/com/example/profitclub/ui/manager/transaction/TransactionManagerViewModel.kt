package com.example.profitclub.ui.manager.transaction

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TransactionManagerViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "You do not have any messages yet"
    }
    val text: LiveData<String> = _text
}