package com.example.profitclub.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.sql.Timestamp

class AuthentificationViewModel: ViewModel() {

    val text = MutableLiveData<String>().apply {
        value = "HelloWorld!!"
    }

    val lname = MutableLiveData<String>().apply {
        value = null
    }

    val fname = MutableLiveData<String>().apply {
        value = null
    }

    val mname = MutableLiveData<String>().apply {
        value = null
    }

    val gender_id = MutableLiveData<Int>().apply {
        value = null
    }

    val date = MutableLiveData<Timestamp>().apply {
        value = null
    }

    val phone = MutableLiveData<String>().apply {
        value = null
    }

    val country_id = MutableLiveData<Int>().apply {
        value = null
    }

    val region_id = MutableLiveData<Int>().apply {
        value = null
    }

    val city_id = MutableLiveData<Int>().apply {
        value = null
    }

    val address = MutableLiveData<String>().apply {
        value = null
    }

    val passport_no = MutableLiveData<String>().apply {
        value = null
    }

    val languages = MutableLiveData<IntArray>().apply {
        value = null
    }

    val categories = MutableLiveData<IntArray>().apply {
        value = null
    }

    val about = MutableLiveData<String>().apply {
        value = null
    }

    val name = MutableLiveData<String>().apply {
        value = null
    }

}