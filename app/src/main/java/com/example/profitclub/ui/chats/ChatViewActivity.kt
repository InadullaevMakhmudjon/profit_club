package com.example.profitclub.ui.chats

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.example.profitclub.R
import com.example.profitclub.databinding.ActivityChatViewBinding
import com.example.profitclub.ui.chats.adapters.SectionPageAdapter4
import kotlinx.android.synthetic.main.activity_chat_view.*
import kotlinx.android.synthetic.main.arbitration_alert_dialog.view.*

class ChatViewActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityChatViewBinding
    private var mSectionPageAdapter: SectionPageAdapter4? = null
    private var role: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat_view)

        setSupportActionBar(toolbar)
        val actionBar = this.supportActionBar

        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayShowCustomEnabled(true)

        val mLayoutInflater: LayoutInflater = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val actionBarView: View = mLayoutInflater.inflate(R.layout.chat_custom_bar, null)
        actionBar?.customView = actionBarView
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
