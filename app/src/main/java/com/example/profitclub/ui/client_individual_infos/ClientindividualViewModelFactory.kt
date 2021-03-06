package com.example.profitclub.ui.client_individual_infos

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.profitclub.data.Service
import com.example.profitclub.data.registration.AboutMeRepository
import com.example.profitclub.data.registration.RegistrationRepository
import com.example.profitclub.data.registration.RegistrationService

class ClientindividualViewModelFactory(val preference: SharedPreferences): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ClientIndividualInfoViewModel::class.java)) {
            return ClientIndividualInfoViewModel(
                repository = RegistrationRepository(
                    retrofit = Service.createService(RegistrationService::class.java)
                ), repository2 = AboutMeRepository(
                    retrofit = Service.createService(RegistrationService::class.java),
                    preference = preference
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}