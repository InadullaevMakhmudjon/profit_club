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
import kotlinx.android.synthetic.main.review_dispute_dialog.view.*

class BidDetailActivity : AppCompatActivity() {
    private lateinit var vm:BidDetailViewModel
    private val APP_PREFERENCE = "MYSETTINGS"
    private var questionId: Int = 0
    private var role: Int? = 0
    private var clientAvatar: String? = null
    private var consultantAvatar: String? = null
    private var nameClient: String? = null
    private var nameConsultant: String? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bid_detail)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val preferences = getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE)
        role = preferences.getInt("role", 0)
        vm = ViewModelProviders.of(this, BidDetailViewModelFactory(preferences)).get(BidDetailViewModel::class.java)

        questionId = intent.getIntExtra("question_id",0)
        if (questionId != 0){
            when (role) {
                2, 4 -> {
                   // vm.postPreview(questionId, "ru")
                    vm.postPreviewClient(questionId, "ru")
                }
                5, 7 -> {
                    vm.postPreviewClient(questionId, "ru")
                }
                else -> toast("Errored...")
            }
        }
                vm.data.observe(this, Observer { data ->
                    if(data != null) {
                        toast("come data")
                        bid_title.text = data.title
                        bid_detail.text = data.description
                        deadline.text = resources.getString(R.string.day) + " " + data.day_deadline.toString() + " " + resources.getString(R.string.hour) + " " + data.hour_deadline
                        price_question.text = resources.getString(R.string.price) + " " + data.price.toString()
                        skills.text = data.categories.reduce{a, b -> "$a/$b"}
                        question_id_desc.text = data.question_id.toString()
                        nameClient = data.client_fullname
                        name_client.text = nameClient
                        stars_client.rating = data.client_rate
                        price.text = data.client_rate.toString()
                        clientAvatar = data.client_avatar
                        if(clientAvatar != null){
                            Picasso.get().load(BASE_URL + data.client_avatar + "sm_avatar.jpg").fit().into(avatar_client)
                        }
                        nameConsultant = data.consultant_fullname
                        name_consultant.text = nameConsultant
                        stars_consultant.rating = data.consultant_rate
                        price_consultant.text = data.consultant_rate.toString()
                        consultantAvatar = data.consultant_avatar
                        if(consultantAvatar != null){
                            Picasso.get().load(BASE_URL + data.consultant_avatar + "sm_avatar.jpg").fit().into(avatar_consultant)
                        }
                    }
                })

                vm.dataClient.observe(this, Observer { result ->
                    if(result != null && result.size > 0) {
                        val data = result[0]
                        toast("come dataClient")
                        bid_title.text = data.title
                        bid_detail.text = data.description
                        deadline.text = resources.getString(R.string.day) + " " + data.day_deadline.toString() + " " + resources.getString(R.string.hour) + " " + data.hour_deadline
                        price_question.text = resources.getString(R.string.price) + " " + data.price.toString()
                        skills.text = data.categories.reduce{a, b -> "$a/$b"}
                        question_id_desc.text = data.question_id.toString()
                        nameClient = data.client_fullname
                        name_client.text = nameClient
                        stars_client.rating = data.client_rate
                        price.text = data.client_rate.toString()
                        clientAvatar = data.client_avatar
                        if(clientAvatar != null){
                            Picasso.get().load(BASE_URL + data.client_avatar + "sm_avatar.jpg").fit().into(avatar_client)
                        }
                        nameConsultant = data.consultant_fullname
                        name_consultant.text = nameConsultant
                        stars_consultant.rating = data.consultant_rate
                        price_consultant.text = data.consultant_rate.toString()
                        consultantAvatar = data.consultant_avatar
                        if(consultantAvatar != null){
                            Picasso.get().load(BASE_URL + data.consultant_avatar + "sm_avatar.jpg").fit().into(avatar_consultant)
                        }
                    }
                })

                vm.error.observe(this, Observer { error ->
                    toast("Error: $error")
                })

        reject.setOnClickListener {
           alertDialogCancel()
        }

            vm.statusCancel.observe(this, Observer { status ->
                if(status.size > 0){
                    if(status[0].question_consultant_end == 1){
                        toast("Completed successfully :-)")
                    } else {
                        toast("Error was occur")
                    }
                }
            })

        close.setOnClickListener {
            alertDialogReview()
        }

        arbitration.setOnClickListener {
            alertDialogDispute()
        }

        vm.statusComplete.observe(this, Observer { status ->
            if (status.question_consultant_end == 1){
                toast("Completed successfully :-)")
            } else {
                toast("Error was occur")
            }
        })

        float_btn.setOnClickListener {
            val intent: Intent = Intent(this, ChatViewActivity::class.java)
            intent.putExtra("question_id", questionId)
            startActivity(intent)
        }
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
                when(role){
                    2, 4 -> vm.postComplete(questionId, description, description2, ratingBar.rating)
                    5, 7 -> vm.postCompleteClient(questionId, description, description2, ratingBar.rating)
                }
            } else{
                toast("All fields should be filled in order to finish the process")
                return@setPositiveButton
            }
        }

        alertDialogBuilder.setNegativeButton(getString(R.string.cancel_alert)) { dialog, which ->
            Toast.makeText(this, "Completed without a review", Toast.LENGTH_SHORT).show()
            dialog?.dismiss()
        }

        alertDialogBuilder.show()
    }

    private fun alertDialogCancel(){
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle(R.string.completed_alert_title)
        alertDialogBuilder.setMessage(getString(R.string.completed_alert_message))
        alertDialogBuilder.setPositiveButton(getString(R.string.confirm)) { dialog, which ->
            when (role){
                2, 4 ->  vm.postCancel(questionId)
                5, 7 -> vm.postCancelClient(questionId)
            }
        }
        alertDialogBuilder.setNegativeButton(getString(R.string.cancel)) { dialog, which ->
            Toast.makeText(this, "Your request directed to the Manager", Toast.LENGTH_SHORT).show()
            dialog?.dismiss()
        }
        alertDialogBuilder.show()
    }

    private fun alertDialogDispute(){
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle(R.string.review_alert)
        val customLayout = layoutInflater.inflate(R.layout.review_dispute_dialog, null)
        val description = customLayout.description.text.toString()

        alertDialogBuilder.setView(customLayout)
        alertDialogBuilder.setMessage(getString(R.string.arbitration_messages))

        alertDialogBuilder.setPositiveButton(getString(R.string.confirm)) { dialog, which ->
            if(description != ""){
               vm.postDisputeOpen(questionId, description)
            } else{
                toast("comment should be given")
                return@setPositiveButton
            }
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

