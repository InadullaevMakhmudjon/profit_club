package com.example.profitclub.ui.account.details

import android.app.DatePickerDialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.profitclub.R
import kotlinx.android.synthetic.main.fragment_profile_details.*
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
import javax.net.ssl.HttpsURLConnection
import kotlin.collections.ArrayList

class ProfileDetailsFragment : Fragment(), DatePickerDialog.OnDateSetListener {
    private lateinit var vm: ProfileDetailsViewModel
    private val APP_PREFERENCE = "MYSETTINGS"
    var lNameText: String? = null
    var fNameText: String? = null
    var mNameText: String? = null
    var phoneText: String? = null
    var addressText: String? = null
    var passportNoText: String? = null
    var aboutText: String? = null
    var userId: Int = 0
    var date: String? = null
    val countryId: Int? = 1
    var regionId: Int? = null
    var cityId: Int? = null
    var genderId: Int? = null
    var media: String? = null
    var image: String? = null
    var idRegion: Int = 1
    var about: String = "about"
    var language: IntArray = intArrayOf(1, 2)
    var category: IntArray = intArrayOf(1, 2)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       // val activityProfile = activity as ProfileActivity

        val lname = view.findViewById<AutoCompleteTextView>(R.id.name_detail)
        val fname = view.findViewById<AutoCompleteTextView>(R.id.last_name_detail)
        val mname = view.findViewById<AutoCompleteTextView>(R.id.patronymic_detail)
        val phone = view.findViewById<AutoCompleteTextView>(R.id.phone_number_detail)
        val passportNo = view.findViewById<AutoCompleteTextView>(R.id.passport_no_detail)
        val address = view.findViewById<AutoCompleteTextView>(R.id.address_detail)
        val dateOfBirth = view.findViewById<TextView>(R.id.date_of_birth_detail)
        val save = view.findViewById<Button>(R.id.save_detail)

        val male = view.findViewById<CheckBox>(R.id.male_detail)
        val maleText = view.findViewById<TextView>(R.id.male_text_detail)
        val female = view.findViewById<CheckBox>(R.id.female_detail)
        val femaleText = view.findViewById<TextView>(R.id.female_text_detail)

        val regionDropDown = view.findViewById<Spinner>(R.id.regions_detail)
        val citiesDropDown = view.findViewById<Spinner>(R.id.cities_detail)



        male.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                maleText.setTextColor(resources.getColor(android.R.color.black))
                female.isChecked = false
                genderId = 2
                // Code to display your message.
            } else
                maleText.setTextColor(resources.getColor(android.R.color.darker_gray))
        }

        female.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                femaleText.setTextColor(resources.getColor(android.R.color.black))
                male.isChecked = false
                genderId = 1
                // Code to display your message.
            } else
                femaleText.setTextColor(resources.getColor(android.R.color.darker_gray))
        }

        activity.let { activity ->
            val preferences = activity!!.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE)
            vm =
                ViewModelProviders.of(this, ProfileDetailsViewModelFactory(preferences)).get(ProfileDetailsViewModel::class.java)

            vm.getUserInfo()

            val items = ArrayList<String>()
            val adapter = ArrayAdapter(activity.baseContext, R.layout.support_simple_spinner_dropdown_item, items)
            regionDropDown.adapter = adapter

            vm.regions.observe(viewLifecycleOwner, androidx.lifecycle.Observer {allRegions->
                if(allRegions.size > 0) {
                    items.clear()
                    val userInfo = vm.userInfo.value
                    if(userInfo != null) {
                        val region = allRegions.find { reg -> reg.id == userInfo.region_id }
                        allRegions.remove(region)
                        if(region != null) allRegions.add(0, region)
                    }
                    items.addAll(allRegions.map { region -> region.name })
                    adapter.notifyDataSetChanged()
                }
            })
            vm.cities.observe(viewLifecycleOwner, androidx.lifecycle.Observer { allCities ->
                if(allCities.size > 0) {
                    //city_text.visibility = INVISIBLE
                    // city_text.isVisible = false
                    citiesDropDown.adapter = ArrayAdapter(activity!!.baseContext, R.layout.support_simple_spinner_dropdown_item, allCities.map { region -> region.name })
                }
            })
            val categories = ArrayList<Int>()

            regionDropDown.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {
                    vm.getCity(vm.regions.value!![position].id)
                    regionId = vm.regions.value!![position].id
                    categories.add(vm.regions.value!![position].id)
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }

            citiesDropDown.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {
                    cityId = vm.cities.value!![position].id

                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }

        vm.userInfo.observe(viewLifecycleOwner, Observer { data ->
            if (data != null){
                userId = data.user_id
                media = data.media_url
                lname.setText(data.lname)
                fname.setText(data.fname)
                mname.setText(data.mname)
                phone.setText(data.phone)
                passportNo.setText(data.info.passport_no)
                address.setText(data.address)
                vm.getCity(data.region_id)
               if (data.gender_id == 1){
                   male.isChecked = true
               } else{
                   female.isChecked = true
               }
                dateOfBirth.text = date(data.bdate)
                this.about.apply { data.about }
                this.language.apply { data.languages }
                this.category.apply { data.categories }
            }
        })

        dateOfBirth.setOnClickListener {
            showDatePickerDialog()
        }

        save.setOnClickListener {

            lNameText = lname.text.toString()
            fNameText = fname.text.toString()
            mNameText = mname.text.toString()
            phoneText = phone.text.toString()
            passportNoText = passportNo.text.toString()
            addressText = address.text.toString()
            date = dateOfBirth.text.toString()

             // Get a picture from activity

            if (userId != 0){
                vm.save(userId, mNameText!!,
                    lNameText!!, fNameText!!, genderId!!, date!!, phoneText!!, countryId!!,
                    regionId!!, cityId!!, addressText!!, passportNoText!!, about,  language, category, 1)
                    activity!!.finish()
            }

           // toast("$userId, $image, $mNameText, $lNameText, $fNameText, $genderId, $date, $phoneText, $countryId, $regionId, $cityId, $addressText, $passportNoText")
        }

        vm.status.observe(viewLifecycleOwner, Observer { status ->
            if (status.status == 0){
                activity!!.finish()
            }
        })
    }

    private fun showDatePickerDialog() {
        var datePickerDialog = context?.let {
            DatePickerDialog(
                it,this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH))
        }
        datePickerDialog?.show()
    }

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        date =  "$p1-${p2+1}-$p3"
        date_of_birth_detail.text = date.toString()
    }

    fun date(string: String): String{
        val readDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S")
        readDate.timeZone = TimeZone.getTimeZone("GMT") // missing line
        val date = readDate.parse(string)
        val writeDate = SimpleDateFormat("yyyy-MM-dd")
        writeDate.timeZone = TimeZone.getTimeZone("GMT+04:00")
        return writeDate.format(date)
    }
}