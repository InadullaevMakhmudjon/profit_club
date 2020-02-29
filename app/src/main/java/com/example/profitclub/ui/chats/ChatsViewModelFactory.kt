package com.example.profitclub.ui.chats

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.profitclub.data.Service
import com.example.profitclub.data.bids.BidsRepository
import com.example.profitclub.data.bids.BidsService

class ChatsViewModelFactory(val preference: SharedPreferences): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ChatsViewModel::class.java)) {
            return ChatsViewModel(
                repository = BidsRepository(
                    retrofit = Service.createService(BidsService::class.java),
                    preference = preference
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}