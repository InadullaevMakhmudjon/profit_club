package com.example.profitclub.ui.create_account

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.profitclub.data.Service
import com.example.profitclub.data.auth.AuthRepository
import com.example.profitclub.data.auth.AuthService

class CreationAccountViewModelFactory(private val preference: SharedPreferences): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CreationAccountViewModel::class.java)) {
            return CreationAccountViewModel(
                repository = AuthRepository(
                    retrofit = Service.createService(AuthService::class.java),
                    preference = preference
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}