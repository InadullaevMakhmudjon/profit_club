package com.example.profitclub.ui.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.profitclub.data.auth.AuthRepository
import com.example.profitclub.data.auth.ChangePasswordBody
import com.example.profitclub.data.auth.ChangePasswordStatus
import kotlinx.coroutines.launch
import java.lang.Exception

class AccountViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is send Fragment"
    }

    val status = MutableLiveData<ChangePasswordStatus>()

    val error = MutableLiveData<String>()

    val text: LiveData<String> = _text

    val logout = fun() = repository.logout()

    val changePassword = fun(old_password: String, new_password:String, password_repeat: String){
        viewModelScope.launch {
            try {
                val response = repository.changePassword(ChangePasswordBody(old_password,
                    new_password, password_repeat, "ru"))
                if (response.isSuccessful){
                    status.apply { value = response.body() }
                }
            } catch (e: Exception){
                error.apply { value = e.message.toString() }
            }
        }
    }
}