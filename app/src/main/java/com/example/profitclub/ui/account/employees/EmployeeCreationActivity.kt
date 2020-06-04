package com.example.profitclub.ui.account.employees

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.profitclub.LocaleManager
import com.example.profitclub.R
import com.example.profitclub.adapters.CategoryAdapter
import com.example.profitclub.adapters.LanguageAdapter
import com.example.profitclub.data.bids.DataBid
import com.example.profitclub.data.bids.Language
import com.example.profitclub.data.registration.GetUserStaffInfoBody
import com.example.profitclub.getImagePath
import com.example.profitclub.toast
import com.example.profitclub.ui.account.details.ProfileDetailsViewModel
import com.example.profitclub.ui.account.details.ProfileDetailsViewModelFactory
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_employee_creation.*
import kotlinx.android.synthetic.main.activity_employee_creation.date_of_birth_detail
import kotlinx.android.synthetic.main.fragment_profile_details.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class EmployeeCreationActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {
    private lateinit var preferences: SharedPreferences
    private val APP_PREFERENCE = "MYSETTINGS"
    private lateinit var vm: ProfileDetailsViewModel
    private lateinit var item: GetUserStaffInfoBody
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
    val categoryIds = ArrayList<Int>()
    var languageIds = ArrayList<Int>()
    val allCategories = ArrayList<DataBid>()
    val allLanguage = arrayListOf(Language(1, "English"), Language(2, "Uzbek"), Language(3, "Русский"))
    var role: Int = 0
    var roleEmployee: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_creation)

        val accEmail = findViewById<AutoCompleteTextView>(R.id.account_email)
        val accPassword = findViewById<AutoCompleteTextView>(R.id.account_password)
        val accPasswordRepeat = findViewById<AutoCompleteTextView>(R.id.account_password_repeat)

        val employeePhoto = findViewById<CircleImageView>(R.id.employee_photo)

        val lname = findViewById<AutoCompleteTextView>(R.id.name_detail)
        val fname = findViewById<AutoCompleteTextView>(R.id.last_name_detail)
        val mname = findViewById<AutoCompleteTextView>(R.id.patronymic_detail)
        val phone = findViewById<AutoCompleteTextView>(R.id.phone_number_detail)
        val passportNo = findViewById<AutoCompleteTextView>(R.id.passport_no_detail)
        val address = findViewById<AutoCompleteTextView>(R.id.address_detail)
        val dateOfBirth = findViewById<TextView>(R.id.date_of_birth_detail)
        val save = findViewById<Button>(R.id.save_detail)
        val container = findViewById<LinearLayout>(R.id.employees_acc_details_container)
        val employeeRole = findViewById<LinearLayout>(R.id.employee_role)
        val male = findViewById<CheckBox>(R.id.male_detail)
        val maleText = findViewById<TextView>(R.id.male_text_detail)
        val female = findViewById<CheckBox>(R.id.female_detail)
        val manager = findViewById<CheckBox>(R.id.manager)
        val customer = findViewById<CheckBox>(R.id.customer)
        val femaleText = findViewById<TextView>(R.id.female_text_detail)
        val employeeLanguages = findViewById<TextView>(R.id.employee_languages)
        val employeeCategories = findViewById<TextView>(R.id.employee_categories)

        val regionDropDown = findViewById<Spinner>(R.id.regions_detail)
        val citiesDropDown = findViewById<Spinner>(R.id.cities_detail)

        when (intent.getIntExtra("key", 0)){
            1 -> {
                item = intent.getSerializableExtra("item") as GetUserStaffInfoBody
                container.isVisible = false
                employeeCategories.isVisible = false
                employeeLanguages.isVisible = false
                save.text = getString(R.string.save)

                lname.setText(item.lname)
                fname.setText(item.fname)
                mname.setText((item.mname))
                phone.setText(item.phone)
                passportNo.setText(item.info.passport_no)
                address.setText(item.address)
                if (item.gender_id == 1){
                    male.isChecked = true
                } else{
                    female.isChecked = true
                }
                if (item.role == "Manager"){
                    manager.isChecked = true
                } else{
                    customer.isChecked = true
                }
                dateOfBirth.text = item.bdate
            }
            2 -> {
                employeeCategories.isVisible = false
                employeeLanguages.isVisible = false
                save.text = getString(R.string.create)
            }
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

        manager.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                manager_text_detail.setTextColor(resources.getColor(android.R.color.black))
                customer.isChecked = false
                roleEmployee = "Manager"
                // Code to display your message.
            } else
                customer_text_detail.setTextColor(resources.getColor(android.R.color.darker_gray))
        }

        customer.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                customer_text_detail.setTextColor(resources.getColor(android.R.color.black))
                manager.isChecked = false
                roleEmployee = "Customer"
                // Code to display your message.
            } else
                manager_text_detail.setTextColor(resources.getColor(android.R.color.darker_gray))
        }

        preferences = getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE)
        vm = ViewModelProviders.of(this, ProfileDetailsViewModelFactory(preferences))
            .get(ProfileDetailsViewModel::class.java)

        val items = ArrayList<String>()
        val adapter = ArrayAdapter(baseContext, R.layout.support_simple_spinner_dropdown_item, items)
        regionDropDown.adapter = adapter

        vm.regions.observe(this, androidx.lifecycle.Observer {allRegions->
            if(allRegions.size > 0) {
                items.clear()
                //val userInfo = vm.userInfo.value
                if(item != null) {
                    val region = allRegions.find { reg -> reg.id == item.region_id }
                    allRegions.remove(region)
                    if(region != null) allRegions.add(0, region)
                }
                items.addAll(allRegions.map { region -> region.name })
                adapter.notifyDataSetChanged()
            }
        })
        vm.cities.observe(this, androidx.lifecycle.Observer { allCities ->
            if(allCities.size > 0) {
                //city_text.visibility = INVISIBLE
                // city_text.isVisible = false
                citiesDropDown.adapter = ArrayAdapter(baseContext, R.layout.support_simple_spinner_dropdown_item, allCities.map { region -> region.name })
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
            when (intent.getIntExtra("key", 0)){
                1 -> {
                    //vm.userStaffEdit()
                }
                2 -> {
                    //vm.postUserStaff()
                }
            }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    @Override
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleManager.setLocale(base))
    }

    private fun showDatePickerDialog() {
        var datePickerDialog = baseContext?.let {
            DatePickerDialog(
                it, this,
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

    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    //handle requested permission result
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    //permission from popup granted
                    pickImageFromGallery()
                }
                else{
                    //permission from popup denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    companion object {
        //image pick code
        private const val IMAGE_PICK_CODE = 1000;
        //Permission code
        private const val PERMISSION_CODE = 1001;
    }

    private fun upload(path: String?) {
        if(path != null) {
            val file = File(path)
            val reqFile = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
            val body = MultipartBody.Part.createFormData("file", file.name, reqFile)
            val id = "$userId".toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val type = "1".toRequestBody("multipart/form-data".toMediaTypeOrNull())

            // Simple Callback example
            val making = {st: String->
                toast(st)
            }

            vm.upload(body, id, type, making)
        }
    }

    //handle result of picked image
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            vm.deletePhoto(userId, 1)
            val image = data?.data
            employee_photo.setImageURI(image)
            vm.uploadPhoto(userId, 1)
            //picture = image.toString() // Set value to picture from gallery
            if(image != null) {
                val path = getImagePath(image)
                upload(path)
            }
        }
    }

    private fun alertDialogCategory(){
        val alertDialogBuilder = AlertDialog.Builder(this)
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

        val adapter = CategoryAdapter(this, allCategories, categoryIds, itemCallBack)
        layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recycler.layoutManager = layoutManager
        recycler.adapter = adapter
        adapter.notifyDataSetChanged()

        vm.categories.observe(this, androidx.lifecycle.Observer { data ->
            if (data != null){
                allCategories.clear()
                allCategories.addAll(data)
                adapter.notifyDataSetChanged()
            }
        })

        alertDialogBuilder.setMessage(getString(R.string.category_alert))

        alertDialogBuilder.setPositiveButton(getString(R.string.confirm)) { dialog, which ->
            Toast.makeText(this, "Categories checked", Toast.LENGTH_SHORT).show()

        }

        alertDialogBuilder.setNegativeButton(getString(R.string.cancel_alert)) { dialog, which ->
            Toast.makeText(this, "You should check at least one of categories", Toast.LENGTH_SHORT).show()
            dialog?.dismiss()
        }

        alertDialogBuilder.show()
    }

    private fun alertDialogLanguage(){
        val alertDialogBuilder = AlertDialog.Builder(this)
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

        val adapter = LanguageAdapter(this, allLanguage, languageIds, itemCallBack)
        layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recycler.layoutManager = layoutManager
        recycler.adapter = adapter
        adapter.notifyDataSetChanged()

        alertDialogBuilder.setMessage(getString(R.string.category_alert))

        alertDialogBuilder.setPositiveButton(getString(R.string.confirm)) { dialog, which ->
            Toast.makeText(this, "Languages checked", Toast.LENGTH_SHORT).show()
        }

        alertDialogBuilder.setNegativeButton(getString(R.string.cancel_alert)) { dialog, which ->
            Toast.makeText(this, "You should check at least one of languages", Toast.LENGTH_SHORT).show()
            dialog?.dismiss()
        }

        alertDialogBuilder.show()
    }

    fun date(string: String): String{
        val readDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S")
        readDate.timeZone = TimeZone.getTimeZone("GMT") // missing line
        val date = readDate.parse(string)
        val writeDate = SimpleDateFormat("dd.MM.yyyy")
        writeDate.timeZone = TimeZone.getTimeZone("GMT+04:00")
        return writeDate.format(date)
    }
}
