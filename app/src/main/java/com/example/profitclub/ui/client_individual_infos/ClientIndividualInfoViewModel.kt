package com.example.profitclub.ui.client_individual_infos

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.profitclub.data.registration.Data
import com.example.profitclub.data.registration.RegistrationRepository
import kotlinx.coroutines.launch

class ClientIndividualInfoViewModel(private val repository: RegistrationRepository) : ViewModel() {

    val regions = MutableLiveData<ArrayList<Data>>().apply {
        value = arrayListOf()
    }

    val cities = MutableLiveData<ArrayList<Data>>().apply {
        value = arrayListOf()
    }

    val error = MutableLiveData<String>().apply {
        value = ""
    }

    val userId = MutableLiveData<Int>().apply {
        value = 0
    }

    val companyId = MutableLiveData<Int>().apply {
        value = 0
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

    val userInfo = fun(login_id: Int, lname: String, fname: String, mname: String, gender_id: Int,
                       date: String, phone: String, country_id: Int, region_id: Int,
                       city_id: Int, address: String, passport_no: String, languages: IntArray,
                       categories: IntArray, about: String){
        viewModelScope.launch {
            try {
                val response = repository.userInfo(login_id, lname, fname, mname, gender_id, date, phone, country_id,
                    region_id, city_id, address, passport_no, languages, categories, about)

                userId.apply { value = response.body()?.user_id}
                companyId.apply { value = response.body()?.company_id }
            } catch (e: Exception){
                error.apply { value = e.message.toString() }
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