package com.example.profitclub

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.profitclub.data.auth.AuthRepository
import kotlinx.coroutines.launch

class MainActivityViewModel(private val repository: AuthRepository): ViewModel() {
    val token = MutableLiveData<String?>().apply {
        value = repository.token
    }

    val isLoggedIn = MutableLiveData<Boolean>().apply {
        value = repository.isLoggedIn
    }

    val loading = MutableLiveData<Boolean>().apply {
        value = false
    }

    val error = MutableLiveData<String>().apply {
        value = ""
    }

    val login = fun(username: String, password: String) {
        viewModelScope.launch {
            try {
                loading.apply { value = true }

                val response = repository.login(username, password)

                token.apply { value = response.body()?.token }

                repository.setUserToken(response.body()?.token)

                loading.apply { value = false }
            } catch (e: Exception) {
                error.apply { value = e.message }
            }
        }
    }

    val logout = fun() = repository.logout()
}