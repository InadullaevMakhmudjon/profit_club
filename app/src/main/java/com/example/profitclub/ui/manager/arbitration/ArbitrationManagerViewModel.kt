package com.example.profitclub.ui.manager.arbitration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ArbitrationManagerViewModel : ViewModel() {


    private val _text = MutableLiveData<String>().apply {
        value = "Total Messages: 10"
    }
    val text: LiveData<String> = _text
}