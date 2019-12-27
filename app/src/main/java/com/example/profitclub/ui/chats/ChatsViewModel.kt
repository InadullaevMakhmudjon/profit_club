package com.example.profitclub.ui.chats

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ChatsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "You do not have any messages yet"
    }
    val text: LiveData<String> = _text
}