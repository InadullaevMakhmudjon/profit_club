package com.example.profitclub.ui.account

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.profitclub.data.Service
import com.example.profitclub.data.auth.AuthRepository
import com.example.profitclub.data.auth.AuthService
import com.example.profitclub.ui.create_account.CreationAccountViewModel

class AccountViewModelFactory(private val preference: SharedPreferences): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(AccountViewModel::class.java)) {
            return AccountViewModel(
                repository = AuthRepository(
                    retrofit = Service.createService(AuthService::class.java),
                    preference = preference
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}