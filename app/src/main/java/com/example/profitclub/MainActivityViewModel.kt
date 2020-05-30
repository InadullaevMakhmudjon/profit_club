package com.example.profitclub

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.profitclub.data.auth.AuthRepository
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

}