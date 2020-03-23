package com.example.profitclub.ui.questions.open

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.profitclub.R
import com.example.profitclub.adapters.BidsAdapter
import com.example.profitclub.data.bids.ConsultantBidsData
import com.example.profitclub.data.questions.QuestionConsultantCancelledData
import com.example.profitclub.data.questions.QuestionConsultantClosedData
import com.example.profitclub.data.questions.QuestionConsultantDisputeData
import com.example.profitclub.toast
import kotlinx.android.synthetic.main.activity_question_detail.*
import kotlinx.android.synthetic.main.review_alert_dialog.view.*
import kotlinx.android.synthetic.main.review_dispute_dialog.view.*

class QuestionDetailActivity : AppCompatActivity(), View.OnClickListener {

    //private lateinit var notificationManager: NotificationManagerCompat
    private var layoutManager: LinearLayoutManager? = null
    private var adapter: BidsAdapter? = null
    private val APP_PREFERENCE = "MYSETTINGS"
    private lateinit var vm: QuestionDetailViewModel
    private var questionId: Int = 0

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_detail)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val preferences = getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE)
        vm = ViewModelProviders.of(this, QuestionsDetailViewModelFactory(preferences)).get(QuestionDetailViewModel::class.java)

        when (this.intent.getIntExtra("role",1)) {
            1 -> {
                val item = intent.getSerializableExtra("item_open") as ConsultantBidsData
                place_bid.isVisible = false
                buttons_container.isVisible = false
                dispute_manager_container_client.isVisible = false
                answer_container_client.isVisible = false
                winner_container_client.isVisible = false
                deadline.isVisible = false
                //container_chosen_consultant.isVisible = false

                //adapter = BidsAdapter(this.applicationContext, null, this)
                layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                this.recycler_bids.layoutManager = layoutManager
               // this.recycler_bids.adapter = adapter
                //adapter?.notifyDataSetChanged()

                title_.text = item.title
                description.text = item.description
                skills_desc.text = item.categories.reduce { a, b -> "$a/$b" }
                question_id_desc.text = item.question_id.toString()
                questionId = item.question_id

                vm.previewBids(questionId)

                    vm.data.observe(this, Observer { data ->
                        if (data != null){
                            adapter = BidsAdapter(this, data.data, this)
                            this.recycler_bids.adapter = adapter
                        }
                    })

                    vm.error.observe(this, Observer { error ->
                        if (error != null)
                            toast("Error: $error")
                    })

                if (vm.data.value != null){
                    if(total_bids != null && recycler_bids != null){
                        val size = vm.data.value!!.count.toString()
                        total_bids.text = "Total bids: $size"
                    }
                }

            }
            2 -> {
                val item = intent.getSerializableExtra("item_dispute") as QuestionConsultantDisputeData
                recycler_bids.isVisible = false
                total_bids.isVisible = false
                buttons_container.isVisible = false
                answer_container_client.isVisible = false
                deadline.isVisible = false
                place_bid.isVisible = false

                title_.text = item.title
                description.text = item.description
                deadline.text = item.dayhour
                skills_desc.text = item.categories.reduce { a, b -> "$a/$b" }
                question_id_desc.text = item.question_id.toString()
                winner_.text = item.true_user_fullname
                dispute_manager_.text = item.answer_dispute
            }
            3 -> {
                val close = intent.getSerializableExtra("item_close") as QuestionConsultantClosedData
                recycler_bids.isVisible = false
                total_bids.isVisible = false
                place_bid.isVisible = false
                buttons_container.isVisible = false
                dispute_manager_container_client.isVisible = false
                winner_container_client.isVisible = false
                deadline.isVisible = false
                answer_container_client.isVisible = false

                title_.text = close.title
                description.text = close.description
                skills_desc.text = close.categories.reduce { a, b -> "$a/$b" }
                question_id_desc.text = close.question_id.toString()
            }
            4 -> {
                val cancel = intent.getSerializableExtra("item_cancel") as QuestionConsultantCancelledData
                recycler_bids.isVisible = false
                total_bids.isVisible = false
                place_bid.isVisible = false
                buttons_container.isVisible = false
                dispute_manager_container_client.isVisible = false
                winner_container_client.isVisible = false

                title_.text = cancel.title
                description.text = cancel.description
                skills_desc.text = cancel.categories.reduce { a, b -> "$a/$b" }
                question_id_desc.text = cancel.question_id.toString()
                deadline.text = cancel.answer_end_date
                answer_.text = cancel.answer_end_description
            }
        }

        //notificationManager = NotificationManagerCompat.from(this)

        /*place_bid.setOnClickListener {
            val intent = Intent(applicationContext, QuestionCreationActivity::class.java)
            intent.putExtra("role", 2)
            startActivity(intent)
        }*/

        if (close != null){
            close.setOnClickListener {
               /* Snackbar.make(it, "Are you sure?", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()*/
                alertDialogReview()
            }
        }

        reject_question_detail.setOnClickListener {
            /* Snackbar.make(it, "Are you sure?", Snackbar.LENGTH_LONG)
                 .setAction("Action", null).show()*/
            alertDialogCancel()
        }

        arbitration_question_detail.setOnClickListener {
            /* Snackbar.make(it, "Are you sure?", Snackbar.LENGTH_LONG)
                 .setAction("Action", null).show()*/
            alertDialogDispute()
        }

      /*  if(total_bids != null && recycler_bids != null){
            val size = adapter!!.itemCount.toString()
            total_bids.text = "Total bids: $size"
        }

        if(date_container != null){
            start_date.text = "Started date: 18/01/2020"
            finished_date.text = "Finished date: 29/01/2020"
        }*/
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onClick(p0: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun alertDialogReview(){
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle(R.string.review_alert)
        val customLayout = layoutInflater.inflate(R.layout.review_alert_dialog, null)
        val description = customLayout.review.text.toString()
        val description2 = customLayout.review_2.text.toString()
        val ratingBar = customLayout.rating_bar
        //val stars: LayerDrawable = ratingBar.progressDrawable as LayerDrawable
        // stars.getDrawable(2).setColorFilter(getColor(R.color.star_color_yellow), PorterDuff.Mode.SRC_ATOP)
        // stars.getDrawable(0).setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP)
        //stars.getDrawable(1).setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP)

        alertDialogBuilder.setView(customLayout)
        alertDialogBuilder.setMessage(getString(R.string.review_desc))

        alertDialogBuilder.setPositiveButton(getString(R.string.confirm)) { dialog, which ->
            //Toast.makeText(this, """You put rating: ${ratingBar.rating}""", Toast.LENGTH_SHORT).show()
            buttons_container.isVisible = false
            if(description != "" && description2 != "" && !ratingBar.rating.equals(0)){
                   // vm.postCompleteClient(questionId, description, description2, ratingBar.rating)
            } else{
                toast(resources.getString(R.string.all_fiels))
                return@setPositiveButton
            }
        }

        alertDialogBuilder.setNegativeButton(getString(R.string.cancel_alert)) { dialog, which ->
            // Toast.makeText(this, "Completed without a review", Toast.LENGTH_SHORT).show()
            dialog?.dismiss()
        }

        alertDialogBuilder.show()
    }

    private fun alertDialogCancel(){
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle(R.string.completed_alert_title)
        alertDialogBuilder.setMessage(getString(R.string.are_you_agree))
        alertDialogBuilder.setPositiveButton(getString(R.string.confirm)) { dialog, which ->
               // vm.postCancelClient(questionId)
            toast(resources.getString(R.string.send_to_manager))
        }
        alertDialogBuilder.setNegativeButton(getString(R.string.cancel)) { dialog, which ->
            //Toast.makeText(this, "Your request directed to the Manager", Toast.LENGTH_SHORT).show()
            dialog?.dismiss()
        }
        alertDialogBuilder.show()
    }

    private fun alertDialogDispute(){
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle(R.string.arbitration)
        val customLayout = layoutInflater.inflate(R.layout.review_dispute_dialog, null)
        val description = customLayout.description.text.toString()

        alertDialogBuilder.setView(customLayout)
        alertDialogBuilder.setMessage(getString(R.string.arbitration_messages))

        alertDialogBuilder.setPositiveButton(getString(R.string.confirm)) { dialog, which ->
            if(description != ""){
               // vm.postDisputeOpen(questionId, description)
            } else{
                //toast("comment should be given")
                return@setPositiveButton
            }
        }

        alertDialogBuilder.setNegativeButton(getString(R.string.cancel_alert)) { dialog, which ->
            //Toast.makeText(this, "Completed without a review", Toast.LENGTH_SHORT).show()
            dialog?.dismiss()
        }
        alertDialogBuilder.show()
    }

   /* private fun notification(){

        val notification = NotificationCompat.Builder(this, App.CHANNEL_1_ID)
            .setSmallIcon(R.drawable.profile_user)
            .setContentTitle("Your bank have been increased!")
            .setContentText("Your work successfully accepted by the client!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .build()

        notificationManager.notify(1, notification)
    }*/

}
