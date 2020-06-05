package com.example.profitclub.ui.questions

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.AutoCompleteTextView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.profitclub.*
import com.example.profitclub.adapters.CategoryAdapter
import com.example.profitclub.data.bids.DataBid
import com.example.profitclub.data.bids.Language
import kotlinx.android.synthetic.main.activity_question_creation.*
import kotlinx.android.synthetic.main.single_language_alert.view.*
import java.util.*
import kotlin.collections.ArrayList

class QuestionCreationActivity : AppCompatActivity() {
    private lateinit var viewModel: QuestionCreationViewModel
    private val APP_PREFERENCE = "MYSETTINGS"
    private var user_id: Int? = null
    private var deadline_final: String? = null
    val categoryIds = ArrayList<Int>()
    var languageId: Int = 0
    val allCategories = ArrayList<DataBid>()
    private val LANGUAGE = "profitclub"
    private var lang: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_creation)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val preferences = getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE)
        val langPreferences = getSharedPreferences(LANGUAGE, Context.MODE_PRIVATE)
        when (langPreferences.getString("lang", null)){
            LANGUAGE_RUSSIAN -> lang = "ru"
            LANGUAGE_ENGLISH -> lang = "en"
            LANGUAGE_UZBEK -> lang = "uz"
        }

        user_id = preferences.getInt("user_id", 0)
        viewModel = ViewModelProviders.of(this, QuestionCreationViewModelFactory(preferences)).get(QuestionCreationViewModel::class.java)

        val title = findViewById<AutoCompleteTextView>(R.id.title_question_creation)
        val description = findViewById<AutoCompleteTextView>(R.id.description_question_creation)
        val price = findViewById<AutoCompleteTextView>(R.id.price_question_creation)
        val deadline = findViewById<TextView>(R.id.question_deadline)
        //val role = this.intent.getIntExtra("role",1)

       /* if(role == 2){
            supportActionBar?.title = "Place a bid"

            language_container.isVisible = false
            category_question_creation.isVisible = false
            title_255.isVisible = false
            description.isVisible = false
        } else{

            price_container.isVisible = false
            brief.isVisible = false
        }*/

        category_question_creation.setOnClickListener {
            this.alertDialogCategory()
        }

        language_question_creation.setOnClickListener {
            this.alertDialogLanguage()
        }

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        question_deadline.setOnClickListener {
            //showDatePickerDialog()
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                // Display Selected date in TextView
                val month = monthOfYear + 1
                question_deadline.text = ("Deadline: $dayOfMonth/$month/$year")
                deadline_final = "$year-$month-$dayOfMonth"
            }, year, month, day)
            dpd.show()
        }

        post.setOnClickListener {
            /*Snackbar.make(it, "Your question posted successfully", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
            */

            if(categoryIds.size > 0 && languageId != 0) {
                viewModel.postQuestion(user_id!!, categoryIds, title.text.toString(), description.text.toString(),
                    languageId, deadline_final.toString())
            }
        }

        this.viewModel.error.observe(this, androidx.lifecycle.Observer {
            toast(it)
        })
        this.viewModel.data.observe(this, androidx.lifecycle.Observer {
            this.finish()
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    /*override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.checkable_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.law, R.id.accounting , R.id.it -> {
                item.isChecked = !item.isChecked
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }*/

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
        viewModel.getCategories(lang!!)

        viewModel.categories.observe(this, androidx.lifecycle.Observer { data ->
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
        val customLayout = layoutInflater.inflate(R.layout.single_language_alert, null)
        alertDialogBuilder.setView(customLayout)
        customLayout.checkbox_english.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                customLayout.name_english.setTextColor(getColor(R.color.colorAccent))
                languageId = 1
                customLayout.checkbox_uzbek.isChecked = false
                customLayout.checkbox_russian.isChecked = false
            } else
                customLayout.name_english.setTextColor(getColor(R.color.black))
        }

        customLayout.checkbox_uzbek.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                customLayout.name_uzbek.setTextColor(getColor(R.color.colorAccent))
                languageId = 2
                customLayout.checkbox_english.isChecked = false
                customLayout.checkbox_russian.isChecked = false
            } else
                customLayout.name_uzbek.setTextColor(getColor(R.color.black))
        }

        customLayout.checkbox_russian.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                customLayout.name_russian.setTextColor(getColor(R.color.colorAccent))
                languageId = 3
                customLayout.checkbox_uzbek.isChecked = false
                customLayout.checkbox_english.isChecked = false
            } else
                customLayout.name_russian.setTextColor(getColor(R.color.black))
        }

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

    @Override
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleManager.setLocale(base))
    }
}
