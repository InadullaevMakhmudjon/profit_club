package com.example.profitclub.ui.account.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.profitclub.data.bids.DataBid
import com.example.profitclub.data.registration.*
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class ProfileDetailsViewModel(val repository: AboutMeRepository) : ViewModel() {

    val userInfo = MutableLiveData<GetUserInfoBody>().apply {
        value = null
    }

    val regions = MutableLiveData<ArrayList<Data>>().apply {
        value = arrayListOf()
    }

    val cities = MutableLiveData<ArrayList<Data>>().apply {
        value = arrayListOf()
    }

    val categories = MutableLiveData<ArrayList<DataBid>>().apply { value = null }

    val status = MutableLiveData<UploadPhotoResponse>()

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

    val deletePhoto = fun(user_id: Int, type: Int){
        viewModelScope.launch {
            try {
                val response = repository.deletePhoto(DeleteUploadPhotoBody(user_id, type))
                if (response.code() == 401){

                }
            } catch (e:Exception){
                error.apply { value = e.message.toString() }
            }
        }
    }

    val uploadPhoto = fun(user_id: Int, type: Int){
        viewModelScope.launch {
            try {
                val response = repository.uploadPhoto(DeleteUploadPhotoBody(user_id, type))
                if (response.isSuccessful){
                    status.apply { value = response.body() }
                }
            } catch (e:Exception){
                error.apply { value = e.message.toString() }
            }
        }
    }

    val save = fun(user_id: Int, lname: String, fname: String, mname: String, gender_id: Int,
                       date: String, phone: String, country_id: Int, region_id: Int,
                       city_id: Int, address: String, passport_no: String, about: String, languages: ArrayList<Int>,
                       categories: ArrayList<Int>){
        viewModelScope.launch {
            try {
                val response = repository.save(
                    PostUserInfoBody(user_id, lname, fname, mname, gender_id, date, phone, country_id,
                        region_id, city_id, address, InfoUser(about, languages, categories, passport_no), "en"))
                if (response.isSuccessful){
                    status.apply { value = response.body() }
                }

            } catch (e: Exception){
                error.apply { value = e.message.toString() }
            }
        }
    }

    val upload = fun(file: MultipartBody.Part, id: RequestBody, type: RequestBody, callBack: (String) -> Unit) {
        viewModelScope.launch {
            try {
                repository.uploadDemo(file, id, type)
                callBack.invoke("Res send")
            } catch (e: java.lang.Exception) {
                error.apply { value = e.message.toString() }
                callBack.invoke(e.message.toString())
            }
        }
    }

    val postUserStaff = fun(userId: Int, email: String, password: String, passwordRepeat: String,
                            lname: String, fname: String, mname: String, genderId: Int, bdate: String,
                            role: String, companyId: Int, passport_no: String, phone: String, country_id: Int,
                            region_id: Int, city_id: Int, address: String){
        viewModelScope.launch {
            try {
                val response = repository.postUserStaff(PostUserStaffInfoBody(
                    userId, email, password, passwordRepeat, lname, fname, mname, genderId, bdate, InfoStuff(role,
                        companyId, passport_no), phone, role, country_id, region_id, city_id, address
                ))
                if (response.isSuccessful){
                    status.apply { value = response.body() }
                }
            } catch (e: Exception){
                error.apply { value = e.message.toString() }
            }
        }
    }

    val userStaffEdit = fun(userId: Int, email: String, password: String, passwordRepeat: String,
                            lname: String, fname: String, mname: String, genderId: Int, bdate: String,
                            role: String, companyId: Int, passport_no: String, phone: String, country_id: Int,
                            region_id: Int, city_id: Int, address: String){
        viewModelScope.launch {
            try {
                val response = repository.userStaffEdit(PostUserStaffInfoBody(
                    userId, email, password, passwordRepeat, lname, fname, mname, genderId, bdate, InfoStuff(role,
                        companyId, passport_no), phone, role, country_id, region_id, city_id, address
                ))
                if (response.isSuccessful){
                    status.apply { value = response.body() }
                }
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
                error.apply { value = e.message.toString() }
            }
        }
    }

    init {
        viewModelScope.launch {
            try {
                val response = repository.getCategories()
                if (response.isSuccessful){
                    categories.apply { value = response.body() }
                }
            } catch (e: Exception){
                error.apply { value = e.message.toString() }
            }
        }
    }
}