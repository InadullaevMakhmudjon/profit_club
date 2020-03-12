package com.example.profitclub.ui.questions.open

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog.*
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.profitclub.R
import com.example.profitclub.adapters.BidsAdapter
import com.example.profitclub.data.bids.ConsultantBidsData
import com.example.profitclub.data.questions.QuestionConsultantDisputeData
import com.example.profitclub.model.Bid
import com.example.profitclub.model.Bids
import com.example.profitclub.toast
import com.example.profitclub.ui.browse.BrowseQuestionViewModel
import com.example.profitclub.ui.questions.QuestionCreationActivity
import kotlinx.android.synthetic.main.activity_question_detail.*
import kotlinx.android.synthetic.main.activity_question_detail.toolbar
import kotlinx.android.synthetic.main.review_alert_dialog.view.*

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
                date_container.isVisible = false
                container_chosen_consultant.isVisible = false

                //adapter = BidsAdapter(this.applicationContext, null, this)
                layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                this.recycler_bids.layoutManager = layoutManager
               // this.recycler_bids.adapter = adapter
                //adapter?.notifyDataSetChanged()

                title_.text = item.title
                description.text = item.description
                deadline.text = item.click_count
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
                date_container.isVisible = false
                container_chosen_consultant.isVisible = false
                delay.isVisible = false
                buttons_container.isVisible = false
                place_bid.isVisible = false
                title_.text = item.title
                description.text = item.description
                deadline.text = item.dayhour
                skills_desc.text = item.categories.reduce { a, b -> "$a/$b" }
                question_id_desc.text = item.question_id.toString()
            }
            3 -> {
                recycler_bids.isVisible = false
                total_bids.isVisible = false
                date_container.isVisible = false
                place_bid.isVisible = false
                container_chosen_consultant.isEnabled = false
            }
            4 -> {
                recycler_bids.isVisible = false
                total_bids.isVisible = false
                place_bid.isVisible = false
                buttons_container.isVisible = false
            }
        }


        //notificationManager = NotificationManagerCompat.from(this)

        place_bid.setOnClickListener {
            val intent = Intent(applicationContext, QuestionCreationActivity::class.java)
            intent.putExtra("role", 2)
            startActivity(intent)
        }

        if (close != null){
            close.setOnClickListener {
               /* Snackbar.make(it, "Are you sure?", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()*/
                alertDialog()
            }
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

     private fun alertDialog(){
        val alertDialogBuilder = Builder(this)
        alertDialogBuilder.setTitle(R.string.completed_alert_title)
        alertDialogBuilder.setMessage(getString(R.string.completed_alert_message))
        alertDialogBuilder.setPositiveButton(getString(R.string.confirm)) { dialog, which ->
            alertDialogReview()

        }
        alertDialogBuilder.setNegativeButton(getString(R.string.complain)) { dialog, which ->
            Toast.makeText(this, "Your request directed to the Manager", Toast.LENGTH_SHORT).show()
            dialog?.dismiss()
        }

        alertDialogBuilder.show()
    }

    private fun alertDialogReview(){
        val alertDialogBuilder = Builder(this)
        alertDialogBuilder.setTitle(R.string.review_alert)
        val customLayout = layoutInflater.inflate(R.layout.review_alert_dialog, null)
        val ratingBar = customLayout.rating_bar
        //val stars: LayerDrawable = ratingBar.progressDrawable as LayerDrawable
       // stars.getDrawable(2).setColorFilter(getColor(R.color.star_color_yellow), PorterDuff.Mode.SRC_ATOP)
       // stars.getDrawable(0).setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP)
        //stars.getDrawable(1).setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP)

        alertDialogBuilder.setView(customLayout)
        alertDialogBuilder.setMessage(getString(R.string.review_desc))

        alertDialogBuilder.setPositiveButton(getString(R.string.confirm)) { dialog, which ->
            Toast.makeText(this, """You put rating: ${ratingBar.rating}""", Toast.LENGTH_SHORT).show()
            buttons_container.isVisible = false
           // notification()
        }

        alertDialogBuilder.setNegativeButton(getString(R.string.cancel_alert)) { dialog, which ->
            Toast.makeText(this, "Completed without a review", Toast.LENGTH_SHORT).show()
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
