package com.example.profitclub.ui.bids.open

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.profitclub.data.Service
import com.example.profitclub.data.bids.BidsRepository
import com.example.profitclub.data.bids.BidsService

class BidsViewModelFactory(val preference: SharedPreferences): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(OpenBidsViewModel::class.java)) {
            return OpenBidsViewModel(
                repository = BidsRepository(
                    retrofit = Service.createService(BidsService::class.java),
                    preference = preference
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}