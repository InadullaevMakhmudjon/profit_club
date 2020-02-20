package com.example.profitclub.ui.create_account

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.profitclub.data.auth.AuthRepository
import kotlinx.coroutines.launch

class CreationAccountViewModel(private val repository: AuthRepository) : ViewModel() {

    val loginId = MutableLiveData<Int?>().apply {
        value = repository.loginId
    }

     val registeringRole = MutableLiveData<Int?>().apply {
        value = repository.registeringRole
    }

    val loading = MutableLiveData<Boolean>().apply {
        value = false
    }

    val error = MutableLiveData<String>().apply {
        value = ""
    }

    val status = MutableLiveData<Int>().apply {
        value = null
    }

    val register = fun(email: String, password: String, password_repeat: String, type: Int){
        viewModelScope.launch {
            try {
                loading.apply { value = true }

                val response = repository.signIn(email, password, password_repeat, type)
                val register = response.body()
                repository.registeringUser(register)

                loginId.apply { value = register?.login_id }
                status.apply { value =  register?.status }
                registeringRole.apply { value = register?.type }
            } catch (e: Exception) {
                error.value.apply { e.message.toString() }
            }
        }
    }
}