package com.example.profitclub.ui.email_check

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.profitclub.data.auth.AuthRepository
import com.example.profitclub.data.auth.MailConfirmStatus
import kotlinx.coroutines.launch
import java.lang.Exception

class EmailCheckViewModel(private val repository: AuthRepository) : ViewModel() {

    val loginId = MutableLiveData<Int?>().apply {
        value = repository.loginId
    }

    // Status of Email
    val status = MutableLiveData<Boolean>().apply {
        value = null
    }

    val statusResend = MutableLiveData<MailConfirmStatus>()

    val error = MutableLiveData<String>().apply {
        value = ""
    }

    val loading = MutableLiveData<Boolean>().apply {
        value = false
    }

    val emailVerify = fun(emailToken: String){
        viewModelScope.launch {
            try {
                loading.apply { value = true }

                val response = repository.mailVerify(emailToken)

                status.apply { value = response.body() }
            } catch (e: Exception){
                error.apply { e.message.toString() }
            }
        }
    }

    val emailResend = fun(loginId: Int, lang: String){
        viewModelScope.launch {
            try {
                val response = repository.mailResend(loginId, lang)
                if (response.isSuccessful){
                    statusResend.apply { value = response.body() }
                }
            } catch (e: Exception){
                error.apply { value = e.message.toString() }
            }
        }
    }

}