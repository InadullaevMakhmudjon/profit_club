package com.example.profitclub.ui.client_individual_infos

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.profitclub.data.bids.DataBid
import com.example.profitclub.data.registration.*
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class ClientIndividualInfoViewModel(private val repository: RegistrationRepository, private val repository2: AboutMeRepository) : ViewModel() {

    val regions = MutableLiveData<ArrayList<Data>>().apply {
        value = arrayListOf()
    }

    val cities = MutableLiveData<ArrayList<Data>>().apply {
        value = arrayListOf()
    }

    val categories = MutableLiveData<ArrayList<DataBid>>().apply { value = null }

    val error = MutableLiveData<String>().apply {
        value = ""
    }

    val userId = MutableLiveData<Int>().apply {
        value = 0
    }

    val companyId = MutableLiveData<Int>().apply {
        value = 0
    }

    val status = MutableLiveData<UploadPhotoResponse>()

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

    val userInfoClientIndividual = fun(login_id: Int, lname: String, fname: String, mname: String, gender_id: Int,
                       date: String, phone: String, country_id: Int, region_id: Int,
                       city_id: Int, address: String, passport_no: String, languages: ArrayList<Int>){
        viewModelScope.launch {
            try {
                val response = repository.userInfoClientIndividual(
                    UserInfoBodyClientIndividual(
                        login_id, lname, fname, mname, gender_id, date, phone, country_id,
                        region_id, city_id, address, InfoIndividual(languages, passport_no)
                    )
                )

                userId.apply { value = response.body()?.user_id }
                companyId.apply { value = response.body()?.company_id }
            } catch (e: Exception){
                error.apply { value = e.message.toString() }
            }
        }
    }

    val userInfoClientLegal = fun(login_id: Int, lname: String, fname: String, mname: String, gender_id: Int,
                                  date: String, phone: String, country_id: Int, region_id: Int,
                                  city_id: Int, address: String, companyName: String, companyPhone: String, companyCountryId: Int,
                                  companyRegionId: Int, companyCityId: Int, companyAddress: String){
        viewModelScope.launch {
            try {
                val response = repository.userInfoClientLegal(login_id, lname, fname, mname, gender_id, date, phone, country_id,
                    region_id, city_id, address, companyName, companyPhone, companyCountryId, companyRegionId, companyCityId,
                        companyAddress)

                userId.apply { value = response.body()?.user_id}
                companyId.apply { value = response.body()?.company_id }
            } catch (e: Exception){
                error.apply { value = e.message.toString() }
            }
        }
    }

    val userInfoConsultantIndividual = fun(login_id: Int, lname: String, fname: String, mname: String, gender_id: Int,
                                           date: String, phone: String, country_id: Int, region_id: Int,
                                           city_id: Int, address: String, about: String, languages: ArrayList<Int>,
                                           categories: ArrayList<Int>, passport_no: String){
        viewModelScope.launch {
            try {
                val response = repository.userInfoConsultantIndividual(
                    UserInfoBodyConsultantIndividual(login_id, lname, fname, mname, gender_id, date, phone, country_id, region_id,
                        city_id, address, UserInfo(about, languages, categories, passport_no)
                    )
                )

                userId.apply { value = response.body()?.user_id}
                companyId.apply { value = response.body()?.company_id }
            } catch (e: Exception){
                error.apply { value = e.message.toString() }
            }
        }
    }

    val userInfoConsultantLegal = fun(login_id: Int, lname: String, fname: String, mname: String, gender_id: Int,
                                      date: String, phone: String, country_id: Int, region_id: Int,
                                      city_id: Int, address: String, companyName: String, companyPhone: String, companyCountryId: Int,
                                      companyRegionId: Int, companyCityId: Int, companyAddress: String){
        viewModelScope.launch {
            try {
                val response = repository.userInfoConsultantLegal(login_id, lname, fname, mname, gender_id, date, phone, country_id,
                    region_id, city_id, address, companyName, companyPhone, companyCountryId, companyRegionId, companyCityId, companyAddress)

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

    val deletePhoto = fun(user_id: Int, type: Int){
        viewModelScope.launch {
            try {
                val response = repository2.deletePhoto(DeleteUploadPhotoBody(user_id, type))
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
                val response = repository2.uploadPhoto(DeleteUploadPhotoBody(user_id, type))
                if (response.isSuccessful){
                    status.apply { value = response.body() }
                }
            } catch (e:Exception){
                error.apply { value = e.message.toString() }
            }
        }
    }

    val upload = fun(file: MultipartBody.Part, id: RequestBody, type: RequestBody, callBack: (String) -> Unit) {
        viewModelScope.launch {
            try {
                repository2.uploadDemo(file, id, type)
                callBack.invoke("Res send")
            } catch (e: java.lang.Exception) {
                error.apply { value = e.message.toString() }
                callBack.invoke(e.message.toString())
            }
        }
    }
}