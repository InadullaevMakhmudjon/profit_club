package com.example.profitclub.data.registration

import retrofit2.Retrofit

class RegistrationRepository(private val retrofit: RegistrationService){

    // All regions
    suspend fun getRegions() = retrofit.getRegions()

    // All cities
    suspend fun getCities(regionId: Int) = retrofit.getCities(PostCityBody(SetRegionId(regionId)))

    // All categories
    suspend fun getCategories() = retrofit.getCategories()

}