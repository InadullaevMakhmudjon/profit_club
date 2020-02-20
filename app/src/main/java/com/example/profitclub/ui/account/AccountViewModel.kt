package com.example.profitclub.ui.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.profitclub.data.auth.AuthRepository

class AccountViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is send Fragment"
    }
    val text: LiveData<String> = _text

    val logout = fun() = repository.logout()
}