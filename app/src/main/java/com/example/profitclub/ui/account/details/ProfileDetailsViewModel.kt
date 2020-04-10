package com.example.profitclub.ui.account.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.profitclub.data.registration.Data
import com.example.profitclub.data.registration.GetUserInfoBody
import com.example.profitclub.data.registration.RegistrationRepository
import kotlinx.coroutines.launch

class ProfileDetailsViewModel(val repository: RegistrationRepository) : ViewModel() {

    val userInfo = MutableLiveData<GetUserInfoBody>().apply {
        value = null
    }

    val regions = MutableLiveData<ArrayList<Data>>().apply {
        value = arrayListOf()
    }

    val cities = MutableLiveData<ArrayList<Data>>().apply {
        value = arrayListOf()
    }

    val error = MutableLiveData<String>()

    val getUserInfo = fun(){
        viewModelScope.launch {
            try {
                val response  = repository.getUserInfo()
                if (response.isSuccessful){
                    userInfo.apply { value = response.body() }
                }
            } catch (e: Exception){
                error.apply { value = e.message.toString() }
            }
        }
    }

    val getCity = fun(region_id: Int){
        viewModelScope.launch {
            try{
                val response = repository.getCities(region_id)
                cities.apply { value = response.body() }
            } catch (e: Exception){
                error.apply { e.message.toString() }
            }
        }
    }

    init {
        viewModelScope.launch {
            try {
                val response = repository.getRegions()
                regions.apply { value = response.body() }
            } catch (e: Exception){
                error.apply { e.message.toString() }
            }
        }
    }
}