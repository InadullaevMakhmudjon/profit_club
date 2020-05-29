package com.example.profitclub.ui.account.employees

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.profitclub.data.Service
import com.example.profitclub.data.registration.AboutMeRepository
import com.example.profitclub.data.registration.RegistrationService

class EmployeesListViewModelFactory(val preference: SharedPreferences): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(EmployeesListViewModel::class.java)) {
            return EmployeesListViewModel(
                repository = AboutMeRepository(
                    retrofit = Service.createService(RegistrationService::class.java),
                    preference = preference
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}