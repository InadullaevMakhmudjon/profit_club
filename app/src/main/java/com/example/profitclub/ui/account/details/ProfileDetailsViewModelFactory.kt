package com.example.profitclub.ui.account.details

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.profitclub.data.Service
import com.example.profitclub.data.registration.RegistrationRepository
import com.example.profitclub.data.registration.RegistrationService

class ProfileDetailsViewModelFactory(val preference: SharedPreferences): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ProfileDetailsViewModel::class.java)) {
            return ProfileDetailsViewModel(
                repository = RegistrationRepository(
                    retrofit = Service.createService(RegistrationService::class.java),
                    preference = preference
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}