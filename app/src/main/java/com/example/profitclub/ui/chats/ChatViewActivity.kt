package com.example.profitclub.ui.chats

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.example.profitclub.App
import com.example.profitclub.R
import com.example.profitclub.databinding.ActivityChatViewBinding
import com.example.profitclub.ui.chats.adapters.SectionPageAdapter4
import com.github.nkzawa.socketio.client.Socket
import kotlinx.android.synthetic.main.activity_chat_view.*
import kotlinx.android.synthetic.main.arbitration_alert_dialog.view.*
import kotlinx.android.synthetic.main.chat_custom_bar.view.*

class ChatViewActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var preferences: SharedPreferences
    private val APP_PREFERENCE = "MYSETTINGS"
    private lateinit var binding: ActivityChatViewBinding
    private var mSectionPageAdapter: SectionPageAdapter4? = null
    private var role: Int = 0
    private var question_id: Int = 0
    private var client_id: Int = 0
    private var consultant_id: Int = 0
    private lateinit var socket: Socket

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferences = getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE)

        socket = (application as App).getSocket
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat_view)
        question_id = this.intent.getIntExtra("question_id", 0)
        client_id = this.intent.getIntExtra("client_id", 0)
        consultant_id = this.intent.getIntExtra("consultant_id", 0)


        setSupportActionBar(toolbar)
        val actionBar = this.supportActionBar

        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayShowCustomEnabled(true)

        val mLayoutInflater: LayoutInflater = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val actionBarView: View = mLayoutInflater.inflate(R.layout.chat_custom_bar, null)
        actionBar?.customView = actionBarView
        actionBarView.question_id.text = question_id.toString()

        // toast("client id: $client_id, consultant_id: $consultant_id")

        //val role = MainActivity().run { getMyData() }
        /*val activity = callingActivity as MainActivity?
        val role = activity!!.getMyData()
*/
        mSectionPageAdapter =
            SectionPageAdapter4(
                supportFragmentManager
            )
        binding.viewPager.adapter = mSectionPageAdapter
        binding.pagerHeader.setupWithViewPager(binding.viewPager)
        role =  this.intent.getIntExtra("role",1)

        if (role == 1 || role == 3){
            binding.buttonsContainer.isVisible = false
        } else {
            binding.client.setOnClickListener {
                alertDialogReview()
            }

            binding.consultant.setOnClickListener {
                alertDialogReview()
            }
        }

       /* when (this.intent.getIntExtra("role",1)) {
            1 -> {

                binding.buttonsContainer.isVisible = false
            }
            2 -> {

            }
        }*/
    }

    val getSocket: Socket
        get() = socket

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun toolbar(){

    }

    override fun onClick(p0: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun getMyDataChat(): Int {
        return role
    }

    val getOtherId get(): Int {
        val userId = preferences.getInt("user_id", 0)
        return if(userId == client_id) consultant_id else client_id
    }

    fun getMyQusetionId(): Int {
        return question_id
    }

    fun getClientConsultantId(): Pair<Int, Int>{
        return Pair(client_id, consultant_id)
    }
   /* private fun alertDialog(){
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle(R.string.completed_alert_title)
        alertDialogBuilder.setMessage(getString(R.string.completed_alert_message))
        alertDialogBuilder.setPositiveButton(getString(R.string.confirm)) { dialog, which ->
            //alertDialogReview()

        }
        alertDialogBuilder.setNegativeButton(getString(R.string.complain)) { dialog, which ->
            Toast.makeText(this, "Your request directed to the Manager", Toast.LENGTH_SHORT).show()
            dialog?.dismiss()
        }

        alertDialogBuilder.show()
    }*/

    override fun onDestroy() {
        super.onDestroy()
        socket.disconnect()
    }

    private fun alertDialogReview(){
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle(R.string.review_alert)
        val customLayout = layoutInflater.inflate(R.layout.arbitration_alert_dialog, null)
        val string = customLayout.comment.text.toString()

        alertDialogBuilder.setView(customLayout)
        alertDialogBuilder.setMessage(getString(R.string.comment_action))

        alertDialogBuilder.setPositiveButton(getString(R.string.confirm)) { dialog, which ->
            Toast.makeText(this, """You post comment: $string""", Toast.LENGTH_SHORT).show()
            finish()
        }

        alertDialogBuilder.setNegativeButton(getString(R.string.cancel_alert)) { dialog, which ->
            Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show()
            dialog?.dismiss()
        }

        alertDialogBuilder.show()
    }
}
