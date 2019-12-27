package com.example.profitclub.ui.questions

import android.app.DatePickerDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.DatePicker
import androidx.core.view.isVisible
import com.example.profitclub.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_question_creation.*
import kotlinx.android.synthetic.main.fragment_client_individual_infos.*
import kotlinx.android.synthetic.main.question_item.*
import java.util.*

class QuestionCreationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_creation)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val role = this.intent.getIntExtra("role",1)

        if(role == 2){
            supportActionBar?.title = "Place a bid"

            language_container.isVisible = false
            category_container.isVisible = false
            title_255.isVisible = false
            description.isVisible = false
        } else{

            price_container.isVisible = false
            brief.isVisible = false
        }

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        question_deadline.setOnClickListener {
            //showDatePickerDialog()
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                // Display Selected date in TextView
                question_deadline.text = ("" + dayOfMonth + " " + month + ", " + year)
            }, year, month, day)
            dpd.show()

        }

        post.setOnClickListener {
            Snackbar.make(it, "Your question posted successfully", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
            finish()
        }
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)

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
