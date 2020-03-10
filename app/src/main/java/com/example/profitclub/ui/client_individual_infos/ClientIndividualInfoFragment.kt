package com.example.profitclub.ui.client_individual_infos

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.navigation.Navigation
import com.example.profitclub.R
import kotlinx.android.synthetic.main.fragment_client_individual_infos.*
import java.util.*
import kotlin.collections.ArrayList

class ClientIndividualInfoFragment : Fragment(), DatePickerDialog.OnDateSetListener  {

    private lateinit var viewModel: ClientIndividualInfoViewModel

    var lNameText: String? = null
    var fNameText: String? = null
    var mNameText: String? = null
    var phoneText: String? = null
    var addressText: String? = null
    var passportNoText: String? = null
    var aboutText: String? = null
    var loginId: Int? = 0
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

        //val textView: TextView = root.findViewById(R.id.text_tools)
        /*toolsViewModel.text.observe(this, Observer {
            textView.text = it
        })*/

        return inflater.inflate(R.layout.fragment_client_individual_infos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val lname = view.findViewById<AutoCompleteTextView>(R.id.name)
        val fname = view.findViewById<AutoCompleteTextView>(R.id.last_name)
        val mname = view.findViewById<AutoCompleteTextView>(R.id.patronymic)
        val phone = view.findViewById<AutoCompleteTextView>(R.id.phone_number)
        val passportNo = view.findViewById<AutoCompleteTextView>(R.id.passport_no)
        val about = view.findViewById<AutoCompleteTextView>(R.id.about)
        val address = view.findViewById<AutoCompleteTextView>(R.id.address)

        val male = view.findViewById<CheckBox>(R.id.male)
        val maleText = view.findViewById<TextView>(R.id.male_text)
        val female = view.findViewById<CheckBox>(R.id.female)
        val femaleText = view.findViewById<TextView>(R.id.female_text)

        val regionDropDown = view.findViewById<Spinner>(R.id.regions)
        val citiesDropDown = view.findViewById<Spinner>(R.id.cities)

        val getStart = view.findViewById<Button>(R.id.get_started)

        lNameText = lname.text.toString()
        fNameText = fname.text.toString()
        mNameText = mname.text.toString()
        phoneText = phone.text.toString()
        passportNoText = passportNo.text.toString()
        aboutText = about.text.toString()
        addressText = address.text.toString()

        date_of_birth.setOnClickListener {
            showDatePickerDialog()
        }

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

        arguments?.let {
            val safeArgs = ClientIndividualInfoFragmentArgs.fromBundle(it)
            //email_check.text = "Role number: ${safeArgs.clientRole}"
            loginId = safeArgs.loginId
            header.text = loginId.toString()
        }

       activity.let {
           viewModel =
               ViewModelProviders.of(this, ClientindividualViewModelFactory()).get(ClientIndividualInfoViewModel::class.java)

           viewModel.regions.observe(viewLifecycleOwner, androidx.lifecycle.Observer {allRegions->
               if(allRegions.size > 0) {
                   regionDropDown.adapter = ArrayAdapter(it!!.baseContext, R.layout.support_simple_spinner_dropdown_item, allRegions.map { region -> region.name })
               }
           })
           viewModel.cities.observe(viewLifecycleOwner, androidx.lifecycle.Observer { allCities ->
               if(allCities.size > 0) {
                   //city_text.visibility = INVISIBLE
                  // city_text.isVisible = false
                   citiesDropDown.adapter = ArrayAdapter(it!!.baseContext, R.layout.support_simple_spinner_dropdown_item, allCities.map { region -> region.name })
               }
           })
           val categories = ArrayList<Int>()

           regionDropDown.onItemSelectedListener = object :
               AdapterView.OnItemSelectedListener {
               override fun onItemSelected(parent: AdapterView<*>,
                                           view: View, position: Int, id: Long) {
                   viewModel.getCity(viewModel.regions.value!![position].id)
                   regionId = viewModel.regions.value!![position].id
                   categories.add(viewModel.regions.value!![position].id)
               }

               override fun onNothingSelected(parent: AdapterView<*>) {
                   // write code to perform some action
               }
           }

           citiesDropDown.onItemSelectedListener = object :
               AdapterView.OnItemSelectedListener {
               override fun onItemSelected(parent: AdapterView<*>,
                                           view: View, position: Int, id: Long) {
                   cityId = viewModel.cities.value!![position].id

               }

               override fun onNothingSelected(parent: AdapterView<*>) {
                   // write code to perform some action
               }
           }

           getStart.setOnClickListener {
               viewModel.userInfo(
                   this.loginId!!, mNameText!!,
                   lNameText!!, fNameText!!, genderId!!, date!!, phoneText!!, countryId!!,
                   regionId!!, cityId!!, addressText!!, passportNoText!!, intArrayOf(1), intArrayOf(1), aboutText!!
               )
           }

           viewModel.userId.observe(activity!!, androidx.lifecycle.Observer { userId ->
               if (userId != 0){
                   Navigation.findNavController(getStart).navigate(R.id.splashScreeen2Action)
               }
           })

         /*  viewModel.userId.observe(activity!!, Observer { userId ->
               if (userId != null){
                   //Navigation.findNavController(getStart).navigate(R.id.splashScreeen2Action)
               }
           })*/
       }

        /*get_started.setOnClickListener {

        }*/
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
            date = "Date of birth: " + p1 + "/" + p2 + 1 + "/" + p3
            date_of_birth.text = date
        }
    }

