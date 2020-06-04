package com.example.profitclub.ui.account.employees

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.profitclub.data.registration.*
import kotlinx.coroutines.launch

class EmployeesListViewModel(val repository: AboutMeRepository) : ViewModel() {

    val staff = MutableLiveData<UserStaffResponse>().apply { value = null }

    val status = MutableLiveData<UploadPhotoResponse>()

    val error = MutableLiveData<String>()

    init {
        viewModelScope.launch {
            try {
                val response = repository.userStaff("ru")
                if (response.isSuccessful){
                    staff.apply { value = response.body() }
                }
            } catch (e:Exception){
                error.apply { value = e.message.toString() }
            }
        }
    }
}