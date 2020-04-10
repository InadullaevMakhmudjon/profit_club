package com.example.profitclub.ui.account.details

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.profitclub.R
import kotlinx.android.synthetic.main.fragment_client_individual_infos.*
import java.util.*

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
    var userId: Int? = 0
    var date: String? = null
    val countryId: Int? = 1
    var regionId: Int? = null
    var cityId: Int? = null
    var genderId: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val lname = view.findViewById<AutoCompleteTextView>(R.id.name_detail)
        val fname = view.findViewById<AutoCompleteTextView>(R.id.last_name_detail)
        val mname = view.findViewById<AutoCompleteTextView>(R.id.patronymic_detail)
        val phone = view.findViewById<AutoCompleteTextView>(R.id.phone_number_detail)
        val passportNo = view.findViewById<AutoCompleteTextView>(R.id.passport_no_detail)
        val about = view.findViewById<AutoCompleteTextView>(R.id.about_detail)
        val address = view.findViewById<AutoCompleteTextView>(R.id.address_detail)
        val dateOfBirth = view.findViewById<TextView>(R.id.date_of_birth_detail)

        val male = view.findViewById<CheckBox>(R.id.male_detail)
        val maleText = view.findViewById<TextView>(R.id.male_text_detail)
        val female = view.findViewById<CheckBox>(R.id.female_detail)
        val femaleText = view.findViewById<TextView>(R.id.female_text_detail)

        val regionDropDown = view.findViewById<Spinner>(R.id.regions_detail)
        val citiesDropDown = view.findViewById<Spinner>(R.id.cities_detail)

        activity.let { activity ->
            val preferences = activity!!.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE)
            vm =
                ViewModelProviders.of(this, ProfileDetailsViewModelFactory(preferences)).get(ProfileDetailsViewModel::class.java)

            vm.getUserInfo()

        }

        vm.userInfo.observe(viewLifecycleOwner, Observer { data ->
            lname.hint = data.lname
            fname.hint = data.fname
            mname.hint = data.mname
            phone.hint = data.phone
            passportNo.hint = data.passport_no
            address.setText(data.address)

        })

        dateOfBirth.setOnClickListener {
            showDatePickerDialog()
        }
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
        date = "Date of birth: " + p1 + "/" + (p2 + 1) + "/" + p3
        date_of_birth.text = date
    }
}