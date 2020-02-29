package com.example.profitclub.ui.bids

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.profitclub.R
import com.example.profitclub.data.BASE_URL
import com.example.profitclub.toast
import com.example.profitclub.ui.chats.ChatViewActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_bid_detail.*
import kotlinx.android.synthetic.main.activity_bid_detail.buttons_container
import kotlinx.android.synthetic.main.activity_bid_detail.close
import kotlinx.android.synthetic.main.activity_bid_detail.deadline
import kotlinx.android.synthetic.main.activity_bid_detail.price
import kotlinx.android.synthetic.main.activity_bid_detail.question_id_desc
import kotlinx.android.synthetic.main.activity_bid_detail.reject
import kotlinx.android.synthetic.main.activity_question_detail.toolbar
import kotlinx.android.synthetic.main.review_alert_dialog.view.*

class BidDetailActivity : AppCompatActivity() {
    private lateinit var vm:BidDetailViewModel
    private val APP_PREFERENCE = "MYSETTINGS"
    private var questionId: Int = 0

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bid_detail)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val preferences = getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE)
        vm = ViewModelProviders.of(this, BidDetailViewModelFactory(preferences)).get(BidDetailViewModel::class.java)

        questionId = intent.getIntExtra("question_id",1)
        /*when (questionId) {
            1 -> {

            }
            2 -> {
                buttons_container.isVisible = false
            }
        }*/
        vm.postPreview(questionId, "ru")
        vm.postPreviewClient(questionId, "ru")

        vm.data.observe(this, Observer { data ->
            if(data != null) {
                toast("come data")
                bid_title.text = data.title
                bid_detail.text = data.description
                deadline.text = resources.getString(R.string.day) + " " + data.day_deadline.toString() + " " + resources.getString(R.string.hour) + " " + data.hour_deadline
                price_question.text = resources.getString(R.string.price) + " " + data.price.toString()
                // var item = ArrayList<String>(data.categories)
                skills.text = data.categories.reduce{a, b -> "$a/$b"}
                question_id_desc.text = data.question_id.toString()
                name_client.text = data.client_fullname
                stars_client.rating = data.client_rate
                price.text = data.client_rate.toString()
                if(data.client_avatar != null){
                    Picasso.get().load(BASE_URL + data.client_avatar + "sm_avatar.jpg").fit().into(avatar_client)
                }

                name_consultant.text = data.consultant_fullname
                stars_consultant.rating = data.consultant_rate
                price_consultant.text = data.consultant_rate.toString()
                if(data.consultant_avatar != null){
                    Picasso.get().load(BASE_URL + data.consultant_avatar + "sm_avatar.jpg").fit().into(avatar_consultant)
                }
            }
        })

        vm.dataClient.observe(this, Observer { result ->
            if(result != null && result.size > 0) {
                val data = result[0]
                toast("come data")
                bid_title.text = data.title
                bid_detail.text = data.description
                deadline.text = resources.getString(R.string.day) + " " + data.day_deadline.toString() + " " + resources.getString(R.string.hour) + " " + data.hour_deadline
                price_question.text = resources.getString(R.string.price) + " " + data.price.toString()
                // var item = ArrayList<String>(data.categories)
                skills.text = data.categories.reduce{a, b -> "$a/$b"}
                question_id_desc.text = data.question_id.toString()
                name_client.text = data.client_fullname
                stars_client.rating = data.client_rate
                price.text = data.client_rate.toString()
                if(data.client_avatar != null){
                    Picasso.get().load(BASE_URL + data.client_avatar + "sm_avatar.jpg").fit().into(avatar_client)
                }

                name_consultant.text = data.consultant_fullname
                stars_consultant.rating = data.consultant_rate
                price_consultant.text = data.consultant_rate.toString()
                if(data.consultant_avatar != null){
                    Picasso.get().load(BASE_URL + data.consultant_avatar + "sm_avatar.jpg").fit().into(avatar_consultant)
                }
            }
        })

        vm.error.observe(this, Observer { error ->
            toast("Error: $error")
        })

        reject.setOnClickListener {
            vm.postCancel(questionId)

        }

        /*vm.statusCancel.observe(this, Observer { status ->
            if(status.question_consultant_end == 1){
                toast("Completed successfully :-)")
            } else {
                toast("Error was occur")
            }
            *//*if(status.size > 0){
                if(status[0].question_consultant_end == 1){
                    toast("Completed successfully :-)")
                } else {
                    toast("Error was occur")
                }
            }*//*
        })*/

        close.setOnClickListener {
            alertDialog()
        }

        /*vm.statusComplete.observe(this, Observer { status ->
            if (status.question_consultant_end == 1){
                toast("Completed successfully :-)")
            } else {
                toast("Error was occur")
            }
        })*/

        float_btn.setOnClickListener {
            startActivity(Intent(this, ChatViewActivity::class.java))
        }
    }

    private fun alertDialog(){
        val alertDialogBuilder = AlertDialog.Builder(this)
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
            Toast.makeText(this, """You put rating: ${ratingBar.rating}""", Toast.LENGTH_SHORT).show()
            buttons_container.isVisible = false
            if(description != "" && description2 != "" && !ratingBar.rating.equals(0)){
                vm.postComplete(questionId, description, description2, ratingBar.rating)
            } else{
                toast("All fields should be filled in order to finish the process")
                return@setPositiveButton
            }
            //notification()
        }

        alertDialogBuilder.setNegativeButton(getString(R.string.cancel_alert)) { dialog, which ->
            Toast.makeText(this, "Completed without a review", Toast.LENGTH_SHORT).show()
            dialog?.dismiss()
        }

        alertDialogBuilder.show()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}

