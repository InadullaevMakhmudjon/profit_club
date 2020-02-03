package com.example.profitclub.ui.chats

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ChatsView1Model : ViewModel() {


    private val _text = MutableLiveData<String>().apply {
        value = "Total Messages: 10"
    }
    val text: LiveData<String> = _text
}