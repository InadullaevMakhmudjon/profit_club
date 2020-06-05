package com.example.profitclub

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.profitclub.data.auth.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel(private val repository: AuthRepository): ViewModel() {
    val role = MutableLiveData<Int?>().apply {
        value = repository.role
    }

    val token = MutableLiveData<String?>().apply {
        value = repository.token
    }

    val status = MutableLiveData<Int?>()

    val step1Status = MutableLiveData<ResetPasswordStep1?>()

    val step2Status = MutableLiveData<ResetPasswordStep1?>()

    val step3Status = MutableLiveData<ResetPasswordStep1?>()

    val isLoggedIn = MutableLiveData<Boolean>().apply {
        value = repository.isLoggedIn
    }

    val loading = MutableLiveData<Boolean>().apply {
        value = false
    }

    val error = MutableLiveData<String>()

    val login = fun(email: String, password: String) {
        viewModelScope.launch {
            try {
                loading.apply { value = true }

                val response = repository.login(email, password)
                val Token = response.body()
                repository.setUserToken(Token)

                token.apply { value = Token?.token }
                role.apply { value = Token?.type }
                status.apply { value = Token?.status }

                loading.apply { value = false }
            } catch (e: Exception) {
                error.apply { value = e.toString() }
            }
        }
    }

    val resetPasswordStep1 = fun(email: String, step: Int, lang: String){
        viewModelScope.launch {
            try {
                val response = repository.resetPasswordStep1(ResetPasswordStep1Body(Email(email), step, lang))
                if (response.isSuccessful){
                    step1Status.apply { value = response.body() }
                }
            } catch (e: Exception){
                error.apply { value = e.message.toString() }
            }
        }
    }

    val resetPasswordStep2 = fun(email: String, hash: String, step: Int, lang: String){
        viewModelScope.launch {
            try {
                val response = repository.resetPasswordStep2(ResetPasswordStep2Body(EmailHash(email, hash), step, lang))
                if (response.isSuccessful){
                    step2Status.apply { value = response.body() }
                }
            } catch (e: Exception){
                error.apply { value = e.message.toString() }
            }
        }
    }

    val resetPasswordStep3 = fun(email: String, hash: String, password: String, password_repeat: String, step: Int, lang: String){
        viewModelScope.launch {
            try {
                val response = repository.resetPasswordStep3(ResetPasswordStep3Body(EmailHashPassword(email, hash, password, password_repeat), step, lang))
                if (response.isSuccessful){
                    step3Status.apply { value = response.body() }
                }
            } catch (e: Exception){
                error.apply { value = e.message.toString() }
            }
        }
    }
}