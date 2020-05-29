package com.example.profitclub.data.registration

class RegistrationRepository(private val retrofit: RegistrationService){
    // All regions
    suspend fun getRegions() = retrofit.getRegions()

    // All cities
    suspend fun getCities(regionId: Int) = retrofit.getCities(PostCityBody(SetRegionId(regionId)))

    // All categories
    suspend fun getCategories() = retrofit.getCategories()

    //UserInfoClientIndividual
    suspend fun userInfoClientIndividual(login_id: Int, lname: String, fname: String, mname: String, gender_id: Int,
                         date: String, phone: String, country_id: Int, region_id: Int,
                         city_id: Int, address: String, passport_no: String, languages: ArrayList<Int>)
            = retrofit.getUserInfoClientIndividual(
        UserInfoBodyClientIndividual(login_id, lname, fname, mname, gender_id, date, phone, country_id,
            region_id, city_id, address, InfoIndividual(languages, passport_no))
    )

    //UserInfoClientLegal
    suspend fun userInfoClientLegal(login_id: Int, lname: String, fname: String, mname: String, gender_id: Int,
                                    date: String, phone: String, country_id: Int, region_id: Int,
                                    city_id: Int, address: String, companyName: String, companyPhone: String, companyCountryId: Int,
                                    companyRegionId: Int, companyCityId: Int, companyAddress: String)
            = retrofit.getUserInfoClientLegal(
        UserInfoBodyClientLegal(login_id, lname, fname, mname, gender_id, date, phone, country_id,
        region_id, city_id, address, CompanyInfo(companyName, companyPhone, companyCountryId, companyRegionId, companyCityId,
            companyAddress))
    )

    //UserInfoConsultantIndividual
    suspend fun userInfoConsultantIndividual(login_id: Int, lname: String, fname: String, mname: String, gender_id: Int,
                                             date: String, phone: String, country_id: Int, region_id: Int,
                                             city_id: Int, address: String, about: String, languages: ArrayList<Int>,
                                             categories: ArrayList<Int>, passport_no: String)
            = retrofit.getUserInfoConsultantIndividual(
        UserInfoBodyConsultantIndividual(login_id, lname, fname, mname, gender_id, date, phone, country_id, region_id,
            city_id, address, UserInfo(about, languages, categories, passport_no))
    )

    //UserInfoConsultantLegal
    suspend fun userInfoConsultantLegal(login_id: Int, lname: String, fname: String, mname: String, gender_id: Int,
                                          date: String, phone: String, country_id: Int, region_id: Int,
                                          city_id: Int, address: String, companyName: String, companyPhone: String, companyCountryId: Int,
                                          companyRegionId: Int, companyCityId: Int, companyAddress: String)
            = retrofit.getUserInfoConsultantLegal(
        UserInfoBodyConsultantLegal(login_id, lname, fname, mname, gender_id, date, phone, country_id,
            region_id, city_id, address, CompanyInfo(companyName, companyPhone, companyCountryId, companyRegionId, companyCityId, companyAddress)
        )
    )
}