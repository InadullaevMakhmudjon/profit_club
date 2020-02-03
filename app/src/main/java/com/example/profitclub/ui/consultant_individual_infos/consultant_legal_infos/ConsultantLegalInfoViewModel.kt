package com.example.profitclub.ui.consultant_individual_infos.consultant_legal_infos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ConsultantLegalInfoViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is tools Fragment"
    }
    val text: LiveData<String> = _text
}