package com.example.profitclub.ui.browse

import android.content.Context
import android.os.Bundle
import android.widget.AutoCompleteTextView
import android.widget.DatePicker
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.profitclub.R
import com.example.profitclub.data.BASE_URL
import com.example.profitclub.data.bids.ConsultantBidsClickData
import com.example.profitclub.data.bids.ConsultantBidsData
import com.example.profitclub.data.questions.QuestionConsultantApproveData
import com.example.profitclub.data.questions.QuestionConsultantCancelledData
import com.example.profitclub.data.questions.QuestionConsultantClosedData
import com.example.profitclub.data.questions.QuestionConsultantDisputeData
import com.example.profitclub.toast
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_browse_questions.*
import kotlinx.android.synthetic.main.activity_browse_questions.buttons_container
import kotlinx.android.synthetic.main.activity_browse_questions.deadline
import kotlinx.android.synthetic.main.activity_browse_questions.place_bid
import kotlinx.android.synthetic.main.activity_browse_questions.question_id
import kotlinx.android.synthetic.main.activity_browse_questions.question_id_desc
import kotlinx.android.synthetic.main.activity_browse_questions.skills
import kotlinx.android.synthetic.main.activity_browse_questions.toolbar
import kotlinx.android.synthetic.main.activity_question_detail.*
import kotlinx.android.synthetic.main.bottom_sheet_client_preview.view.*

class BrowseQuestionsActivity : AppCompatActivity() {
    private lateinit var data: ConsultantBidsData
    private lateinit var item: ConsultantBidsClickData
    private lateinit var dispute: QuestionConsultantDisputeData
    private lateinit var approve: QuestionConsultantApproveData
    private lateinit var cancel: QuestionConsultantCancelledData
    private lateinit var close: QuestionConsultantClosedData

    private lateinit var vm: BrowseQuestionViewModel
    private val APP_PREFERENCE = "MYSETTINGS"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browse_questions)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val preferences = getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE)
        vm = ViewModelProviders.of(this, BrowseQuestionViewModelFactory(preferences)).get(BrowseQuestionViewModel::class.java)

        when (intent.getIntExtra("key", 0)){
            1 -> {
               item = intent.getSerializableExtra("item_open") as ConsultantBidsClickData
                client.isVisible = false
                place_bid.isVisible = false
                buttons_container.isVisible = false
                dispute_manager_container.isVisible = false
                winner_container.isVisible = false
                answer_conteiner.isVisible = false

                bid_title.text = item.title
                bid_detail.text = item.description
                price_question.text = item.price
                deadline.text = item.deadline
                skills.text = item.categories.reduce {a, b -> "$a/$b"}
                question_id.text = "Bid_id"
                question_id_desc.text = item.bid_id.toString()
           }
            2 -> {
                data = intent.getSerializableExtra("item_browse") as ConsultantBidsData
                buttons_container.isVisible = false
                dispute_manager_container.isVisible = false
                winner_container.isVisible = false
                answer_conteiner.isVisible = false
                deadline.isVisible = false
                price_question.isVisible = false

                bid_title.text = data.title
                bid_detail.text = data.description
                skills.text = data.categories.reduce {a, b -> "$a/$b"}
                question_id_desc.text = data.question_id.toString()
            }
            3 -> {
                dispute = intent.getSerializableExtra("item_dispute") as QuestionConsultantDisputeData
                buttons_container.isVisible = false
                place_bid.isVisible = false
                client.isVisible = false
                answer_conteiner.isVisible = false
                deadline.isVisible = false

                bid_title.text = dispute.title
                bid_detail.text = dispute.description
                price_question.text = dispute.price.toString()
                skills.text = dispute.categories.reduce { a, b -> "$a/$b" }
                question_id_desc.text = dispute.question_id.toString()
                dispute_manager.text = dispute.answer_dispute
                winner.text = dispute.true_user_fullname
            }
            4 -> {
                approve = intent.getSerializableExtra("item_approve") as QuestionConsultantApproveData
                buttons_container.isVisible = false
                place_bid.isVisible = false
                client.isVisible = false
                dispute_manager_container.isVisible = false
                winner_container.isVisible = false
                answer_conteiner.isVisible = false
                deadline.isVisible = false

                bid_title.text = approve.title
                bid_detail.text = approve.description
                price_question.text = approve.price
                skills.text = approve.categories.reduce { a, b -> "$a/$b" }
                question_id_desc.text = approve.question_id.toString()
            }
            5 -> {
                cancel = intent.getSerializableExtra("item_cancel") as QuestionConsultantCancelledData
                buttons_container.isVisible = false
                place_bid.isVisible = false
                client.isVisible = false
                dispute_manager_container.isVisible = false
                winner_container.isVisible = false

                bid_title.text = cancel.title
                bid_detail.text = cancel.description
                price_question.text = cancel.price
                skills.text = cancel.categories.reduce { a, b -> "$a/$b" }
                question_id_desc.text = cancel.question_id.toString()
                answer.text = cancel.answer_end_description
                deadline.text = cancel.answer_end_date
            }
            6 -> {
                close = intent.getSerializableExtra("item_close") as QuestionConsultantClosedData
                buttons_container.isVisible = false
                place_bid.isVisible = false
                client.isVisible = false
                dispute_manager_container.isVisible = false
                winner_container.isVisible = false
                answer_conteiner.isVisible = false
                deadline.isVisible = false

                bid_title.text = close.title
                bid_detail.text = close.description
                price_question.text = close.price
                deadline.text = close.answer_end_date
                skills.text = close.categories.reduce { a, b -> "$a/$b" }
                question_id_desc.text = close.question_id.toString()
            }
        }

      /*  if (key == 1){

        } else {

        }*/

       if (client != null){
           client.setOnClickListener {
               vm.previewClient(data.question_id.toString())
           }
       }

        vm.previewClientResponse.observe(this, Observer {response ->
            val bottomSheetDialog = BottomSheetDialog(this)
            val sheetView = layoutInflater.inflate(R.layout.bottom_sheet_client_preview, null)
            bottomSheetDialog.setContentView(sheetView)

            Picasso.get().load(BASE_URL + response.media_url + "/sm_avatar.jpg").fit().into(sheetView.image_client)
            sheetView.fullname.text = response.fullname
            sheetView.rating_client.rating = response.rate
            sheetView.deadline_question.text = response.deadline

            bottomSheetDialog.show()
        })

        place_bid.setOnClickListener {
            alertDialogPostBid()
        }

        vm.bidResponse.observe(this, Observer { status ->
            if (status != null){
                finish()
                toast(resources.getString(R.string.successfully))
            }
        })
    }

    private fun alertDialogPostBid(){
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle(R.string.bid_creation)
        val customLayout = layoutInflater.inflate(R.layout.bid_post_dialog, null)
        alertDialogBuilder.setView(customLayout)
        //alertDialogBuilder.setMessage(getString(R.string.arbitration_messages))

        val deadlineCalendarView = customLayout.findViewById<DatePicker>(R.id.deadline_alert)
        val priceView = customLayout.findViewById<AutoCompleteTextView>(R.id.price_alert)

        alertDialogBuilder.setPositiveButton(getString(R.string.confirm)) { dialog, which ->
            val date: String = deadlineCalendarView.dayOfMonth.toString() + "-" + (deadlineCalendarView.month + 1).toString() + "-" + deadlineCalendarView.year.toString()
            val price = priceView.text.toString()
            if(date != "" && price != ""){
                vm.placeBid(data.question_id, date, price.toFloat())
            } else{
                //toast("comment should be given")
            }
        }

        alertDialogBuilder.setNegativeButton(getString(R.string.cancel_alert)) { dialog, which ->
            //Toast.makeText(this, "Completed without a review", Toast.LENGTH_SHORT).show()
            dialog?.dismiss()
        }

        alertDialogBuilder.show()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
