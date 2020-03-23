package com.example.profitclub.ui.questions

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.AutoCompleteTextView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.profitclub.R
import kotlinx.android.synthetic.main.activity_question_creation.*
import java.util.*

class QuestionCreationActivity : AppCompatActivity() {
    private lateinit var viewModel: QuestionCreationViewModel
    private val APP_PREFERENCE = "MYSETTINGS"
    private var user_id: Int? = null
    private var deadline_final: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_creation)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val preferences = getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE)
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
            this.alertDialog()
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
                deadline_final = "$dayOfMonth/$month/$year"
            }, year, month, day)
            dpd.show()

        }

        post.setOnClickListener {
            /*Snackbar.make(it, "Your question posted successfully", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
            finish()*/
            viewModel.postQuestion(user_id!!, arrayListOf(1,2), title.text.toString(), description.text.toString(),
                1, deadline_final.toString())
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
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
    }

    private fun alertDialog(){
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Categories")
        val customLayout = layoutInflater.inflate(R.layout.category_alert_dialog, null)
        alertDialogBuilder.setView(customLayout)
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

    /*private fun showDatePickerDialog(view: View) {
        var datePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{view,
            question_text.text = "" +
            ), Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.MONTH),
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH)},


        datePickerDialog?.show()
    }*/


}
