package com.example.profitclub.ui.client_individual_infos

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.profitclub.R
import com.example.profitclub.adapters.CategoryAdapter
import com.example.profitclub.adapters.LanguageAdapter
import com.example.profitclub.data.bids.DataBid
import com.example.profitclub.data.bids.Language
import com.example.profitclub.getImagePath
import com.example.profitclub.toast
import kotlinx.android.synthetic.main.fragment_client_individual_infos.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

class ClientIndividualInfoFragment : Fragment(), DatePickerDialog.OnDateSetListener{

    private lateinit var viewModel: ClientIndividualInfoViewModel
    var picture: String? = null // picture
    private val APP_PREFERENCE = "MYSETTINGS"
    private lateinit var preferences: SharedPreferences
    val categoryIds = ArrayList<Int>()
    var languageIds = ArrayList<Int>()
    val allCategories = ArrayList<DataBid>()
    val allLanguage = arrayListOf(Language(1, "English"), Language(2, "Uzbek"), Language(3, "Русский"))
    var role: Int = 0

    var lNameText: String? = null
    var nameCompanyText: String? = null
    var fNameText: String? = null
    var mNameText: String? = null
    var phoneText: String? = null
    var phoneCompanyText: String? = null
    var addressText: String? = null
    var addressCompanyText: String? = null
    var passportNoText: String? = null
    var aboutText: String? = null
    var loginId: Int = 0
    var date: String? = null
    val countryId: Int? = 1
    var regionId: Int? = null
    var cityId: Int? = null
    var companyRegionId: Int? = null
    var companyCityId: Int? = null
    var genderId: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_client_individual_infos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val lname = view.findViewById<AutoCompleteTextView>(R.id.name)
        val nameCompany = view.findViewById<AutoCompleteTextView>(R.id.company_name)
        val fname = view.findViewById<AutoCompleteTextView>(R.id.last_name)
        val mname = view.findViewById<AutoCompleteTextView>(R.id.patronymic)
        val phone = view.findViewById<AutoCompleteTextView>(R.id.phone_number)
        val phoneCompany = view.findViewById<AutoCompleteTextView>(R.id.company_phone)
        val passportNo = view.findViewById<AutoCompleteTextView>(R.id.passport_no)
        val about = view.findViewById<AutoCompleteTextView>(R.id.about)
        val address = view.findViewById<AutoCompleteTextView>(R.id.address)
        val addressCompany = view.findViewById<AutoCompleteTextView>(R.id.company_address)
        val languages = view.findViewById<TextView>(R.id.languages)
        val categories = view.findViewById<TextView>(R.id.categories)

        val male = view.findViewById<CheckBox>(R.id.male)
        val maleText = view.findViewById<TextView>(R.id.male_text)
        val female = view.findViewById<CheckBox>(R.id.female)
        val femaleText = view.findViewById<TextView>(R.id.female_text)

        val regionDropDown = view.findViewById<Spinner>(R.id.regions)
        val citiesDropDown = view.findViewById<Spinner>(R.id.cities)
        val regionCompanyDropDown = view.findViewById<Spinner>(R.id.regions_company)
        val citiesCompanyDropDown = view.findViewById<Spinner>(R.id.cities_company)

        val getStart = view.findViewById<Button>(R.id.get_started)

        user_photo.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (ContextCompat.checkSelfPermission(activity!!, android.Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED){
                    //permission denied
                    val permissions = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE);
                    //show popup to request runtime permission
                    requestPermissions(permissions, PERMISSION_CODE);
                }
                else{
                    //permission already granted
                    pickUserImageFromGallery();
                }
            }
            else{
                //system OS is < Marshmallow
                pickUserImageFromGallery();
            }
        }

        company_logo.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (ContextCompat.checkSelfPermission(activity!!, android.Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED){
                    //permission denied
                    val permissions = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE);
                    //show popup to request runtime permission
                    requestPermissions(permissions, PERMISSION_CODE);
                }
                else{
                    //permission already granted
                    pickLogoImageFromGallery()
                }
            }
            else{
                //system OS is < Marshmallow
                pickLogoImageFromGallery();
            }
        }

        date_of_birth.setOnClickListener {
            showDatePickerDialog()
        }

        categories.setOnClickListener {
            alertDialogCategory()
        }

        languages.setOnClickListener {
            alertDialogLanguage()
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
            //header.text = loginId.toString()
        }

       activity.let {
           preferences = activity!!.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE)
           role = preferences.getInt("role", 0)
           //lname.setText(role.toString())
           viewModel =
               ViewModelProviders.of(this, ClientindividualViewModelFactory(preferences)).get(ClientIndividualInfoViewModel::class.java)

           when (role){
               2 -> {
                   container_company.isVisible = false
               }
               4 -> {
                   languages.isVisible = false
                   categories.isVisible = false
                   passportNo.isVisible = false
                   about.isVisible = false
               }
               5 -> {
                   container_company.isVisible = false
                   about.isVisible = false
                   categories.isVisible = false
               }
               7 -> {
                   passportNo.isVisible = false
                   about.isVisible = false
                   languages.isVisible = false
                   categories.isVisible = false
               }
           }
           viewModel.regions.observe(viewLifecycleOwner, androidx.lifecycle.Observer { allRegions->
               if(allRegions.size > 0) {
                   regionDropDown.adapter = ArrayAdapter(it!!.baseContext, R.layout.support_simple_spinner_dropdown_item, allRegions.map { region -> region.name })
                   regionCompanyDropDown.adapter = ArrayAdapter(it!!.baseContext, R.layout.support_simple_spinner_dropdown_item, allRegions.map { region -> region.name })

               }
           })
           viewModel.cities.observe(viewLifecycleOwner, androidx.lifecycle.Observer { allCities ->
               if(allCities.size > 0) {
                   citiesDropDown.adapter = ArrayAdapter(it!!.baseContext, R.layout.support_simple_spinner_dropdown_item, allCities.map { region -> region.name })
                   citiesCompanyDropDown.adapter = ArrayAdapter(it!!.baseContext, R.layout.support_simple_spinner_dropdown_item, allCities.map { region -> region.name })
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

           if (container_company.isVisible){
               val categories2 = ArrayList<Int>()
               regionCompanyDropDown.onItemSelectedListener = object :
                   AdapterView.OnItemSelectedListener {
                   override fun onItemSelected(parent: AdapterView<*>,
                                               view: View, position: Int, id: Long) {
                       viewModel.getCity(viewModel.regions.value!![position].id)
                       companyRegionId = viewModel.regions.value!![position].id
                       categories2.add(viewModel.regions.value!![position].id)
                   }

                   override fun onNothingSelected(parent: AdapterView<*>) {
                       // write code to perform some action
                   }
               }

               citiesCompanyDropDown.onItemSelectedListener = object :
                   AdapterView.OnItemSelectedListener {
                   override fun onItemSelected(parent: AdapterView<*>,
                                               view: View, position: Int, id: Long) {
                       companyCityId = viewModel.cities.value!![position].id

                   }

                   override fun onNothingSelected(parent: AdapterView<*>) {
                       // write code to perform some action
                   }
               }
           }

           getStart.setOnClickListener {
                lNameText = lname.text.toString()
                nameCompanyText = nameCompany.text.toString()
                fNameText = fname.text.toString()
                mNameText = mname.text.toString()
                phoneText = phone.text.toString()
                phoneCompanyText = phoneCompany.text.toString()
                passportNoText = passportNo.text.toString()
                aboutText = about.text.toString()
                addressText = address.text.toString()
                addressCompanyText = addressCompany.text.toString()
               when (role){
                   2 -> {
                       if (mNameText != null && lNameText != null && fNameText != null && genderId != 0 && date != null
                           && phoneText != null && regionId != 0 && cityId != 0 && addressText != null && passportNoText != null && aboutText != null
                           && languageIds.size > 0 && categoryIds.size > 0){
                           viewModel.userInfoConsultantIndividual(
                               loginId, mNameText!!, lNameText!!, fNameText!!, genderId!!, date!!, phoneText!!, countryId!!,
                               regionId!!, cityId!!, addressText!!, aboutText!!,
                               languageIds, categoryIds, passportNoText!!
                           )
                       } else {
                           toast(resources.getString(R.string.all_fiels))
                       }
                   }
                   4 -> {
                       if (mNameText != null && lNameText != null && fNameText != null && genderId != 0 && date != null
                           && phoneText != null && regionId != 0 && cityId != 0 && addressText != null && nameCompanyText != null
                           && phoneCompanyText != null && companyRegionId != 0 && companyCityId != 0 && addressCompanyText != "") {
                           viewModel.userInfoConsultantLegal(
                               loginId, mNameText!!, lNameText!!, fNameText!!, genderId!!, date!!, phoneText!!, countryId!!,
                               regionId!!, cityId!!, addressText!!, nameCompanyText!!, phoneCompanyText!!, countryId, companyRegionId!!,
                               companyCityId!!, addressCompanyText!!
                           )
                       } else {
                           toast(resources.getString(R.string.all_fiels))
                       }
                   }
                   5 -> {
                       if (mNameText != null && lNameText != null && fNameText != null && genderId != 0 && date != null
                           && phoneText != null && regionId != 0 && cityId != 0 && addressText != null && passportNoText != null
                           && languageIds.size > 0) {
                           viewModel.userInfoClientIndividual(
                               this.loginId, mNameText!!, lNameText!!, fNameText!!, genderId!!, date!!, phoneText!!, countryId!!,
                               regionId!!, cityId!!, addressText!!, passportNoText!!, languageIds
                           )
                       } else {
                           toast(resources.getString(R.string.all_fiels))
                       }
                   }
                   7 -> {
                       if (mNameText != null && lNameText != null && fNameText != null && genderId != 0 && date != null
                           && phoneText != null && regionId != 0 && cityId != 0 && addressText != null && nameCompanyText != null
                           && phoneCompanyText != null && companyRegionId != 0 && companyCityId != 0 && addressCompanyText != null) {
                           viewModel.userInfoClientLegal(
                               this.loginId, mNameText!!, lNameText!!, fNameText!!, genderId!!, date!!, phoneText!!, countryId!!,
                               regionId!!, cityId!!, addressText!!, nameCompanyText!!, phoneCompanyText!!, countryId,
                               companyRegionId!!, companyCityId!!, addressCompanyText!!
                           )
                       } else {
                           toast(resources.getString(R.string.all_fiels))
                       }
                   }
               }
              /* if (mNameText != null && lNameText != null && fNameText != null && genderId != 0 && date != null
                   && phoneText != null && addressText != null && passportNoText != null && aboutText != null){
                   viewModel.userInfo(
                       this.loginId!!, mNameText!!,
                       lNameText!!, fNameText!!, genderId!!, date!!, phoneText!!, countryId!!,
                       regionId!!, cityId!!, addressText!!, passportNoText!!, intArrayOf(1), intArrayOf(1), aboutText!!)
               } else {
                   toast(getString(R.string.all_fiels))
               }*/
           }

           viewModel.userId.observe(activity!!, Observer{ userId ->
               if (userId != 0) {
                   Navigation.findNavController(getStart).navigate(R.id.actionToLogin)
               }
           })
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
            date =  "$p1-${p2+1}-$p3"
            date_of_birth.text = date
        }

    private fun pickUserImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, USER_IMAGE_PICK_CODE)
    }

    private fun pickLogoImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, LOGO_IMAGE_PICK_CODE)
    }

    private fun upload(path: String?, key: String) {
        if(path != null) {
            val file = File(path)
            val reqFile = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
            val body = MultipartBody.Part.createFormData("file", file.name, reqFile)
            val id = "$loginId".toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val type = key.toRequestBody("multipart/form-data".toMediaTypeOrNull())

            // Simple Callback example
            val making = {st: String ->
                toast(st)
            }

            viewModel.upload(body, id, type, making)
        }
    }

    //handle result of picked image
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == Companion.USER_IMAGE_PICK_CODE){
            viewModel.deletePhoto(loginId, 1)
            val image = data?.data
            user_photo.setImageURI(image)
            viewModel.uploadPhoto(loginId, 1)
            picture = image.toString() // Set value to picture from gallery
            if(image != null) {
                val path = getImagePath(image)
                upload(path, "1")
            }
        }
        if (resultCode == Activity.RESULT_OK && requestCode == Companion.LOGO_IMAGE_PICK_CODE){
            viewModel.deletePhoto(loginId, 2)
            val image = data?.data
            company_logo.setImageURI(image)
            viewModel.uploadPhoto(loginId, 2)
            picture = image.toString() // Set value to picture from gallery
            if(image != null) {
                val path = getImagePath(image)
                upload(path, "2")
            }
        }
    }

    //handle requested permission result
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    //permission from popup granted
                    pickUserImageFromGallery()
                }
                else{
                    //permission from popup denied
                    Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    companion object {
        //Permission code
        private const val PERMISSION_CODE = 1001;
        //User image pick code
        private const val USER_IMAGE_PICK_CODE = 1000;
        //Logo image pick code
        private const val LOGO_IMAGE_PICK_CODE = 1002;
    }

    private fun alertDialogCategory(){
        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setTitle("Categories")
        val customLayout = layoutInflater.inflate(R.layout.category_alert_dialog, null)
        alertDialogBuilder.setView(customLayout)
        val recycler = customLayout.findViewById<RecyclerView>(R.id.category_list)

        var layoutManager: LinearLayoutManager? = null

        val itemCallBack = {id: Int, checked: Boolean ->
            if(checked) {
                categoryIds.add(id)
            } else {
                categoryIds.remove(id)
            }
        }

        val adapter = CategoryAdapter(context!!, allCategories, categoryIds, itemCallBack)
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recycler.layoutManager = layoutManager
        recycler.adapter = adapter
        adapter.notifyDataSetChanged()

        viewModel.categories.observe(viewLifecycleOwner, androidx.lifecycle.Observer { data ->
            if (data != null){
                allCategories.clear()
                allCategories.addAll(data)
                adapter.notifyDataSetChanged()
            }
        })

        alertDialogBuilder.setMessage(getString(R.string.category_alert))

        alertDialogBuilder.setPositiveButton(getString(R.string.confirm)) { dialog, which ->
            Toast.makeText(context, "Categories checked", Toast.LENGTH_SHORT).show()

        }

        alertDialogBuilder.setNegativeButton(getString(R.string.cancel_alert)) { dialog, which ->
            Toast.makeText(context, "You should check at least one of categories", Toast.LENGTH_SHORT).show()
            dialog?.dismiss()
        }

        alertDialogBuilder.show()
    }

    private fun alertDialogLanguage(){
        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setTitle("Languages")
        val customLayout = layoutInflater.inflate(R.layout.category_alert_dialog, null)
        alertDialogBuilder.setView(customLayout)

        val recycler = customLayout.findViewById<RecyclerView>(R.id.category_list)

        var layoutManager: LinearLayoutManager? = null

        val itemCallBack = {id: Int, checked: Boolean ->
            if(checked) {
                languageIds.add(id)
            } else {
                languageIds.remove(id)
            }
        }

        val adapter = LanguageAdapter(context!!, allLanguage, languageIds, itemCallBack)
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recycler.layoutManager = layoutManager
        recycler.adapter = adapter
        adapter.notifyDataSetChanged()

        alertDialogBuilder.setMessage(getString(R.string.category_alert))

        alertDialogBuilder.setPositiveButton(getString(R.string.confirm)) { dialog, which ->
            Toast.makeText(context, "Languages checked", Toast.LENGTH_SHORT).show()
        }

        alertDialogBuilder.setNegativeButton(getString(R.string.cancel_alert)) { dialog, which ->
            Toast.makeText(context, "You should check at least one of languages", Toast.LENGTH_SHORT).show()
            dialog?.dismiss()
        }

        alertDialogBuilder.show()
    }
}