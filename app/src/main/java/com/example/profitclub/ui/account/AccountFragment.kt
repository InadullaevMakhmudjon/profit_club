package com.example.profitclub.ui.account

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.profitclub.*
import com.example.profitclub.ui.AuthentificationActivity
import com.example.profitclub.ui.account.details.ProfileActivity
import com.example.profitclub.ui.account.employees.EmployeesListActivity
import kotlinx.android.synthetic.main.activity_chat_view.*
import kotlinx.android.synthetic.main.arbitration_alert_dialog.view.*
import kotlinx.android.synthetic.main.fragment_account.*

class AccountFragment : Fragment() {

    private lateinit var preferences: SharedPreferences
    private val APP_PREFERENCE = "MYSETTINGS"

    private lateinit var sendViewModel: AccountViewModel
    private lateinit var penalty: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val activity = activity as MainActivity?
        val myDataFromActivity = activity!!.getMyData()
        preferences = context!!.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE)
        sendViewModel =
           ViewModelProviders.of(this, AccountViewModelFactory(preferences)).get(AccountViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_account, container, false)
        //val textView: TextView = root.findViewById(R.id.text_send)
        /*sendViewModel.text.observe(this, Observer {
           // textView.text = it
        })*/
        val logOut: TextView = root.findViewById(R.id.log_out)
        this.penalty = root.findViewById(R.id.num_penalty)
        val employeeList: ConstraintLayout = root.findViewById(R.id.employees_container)
        val viewContainer: ConstraintLayout = root.findViewById(R.id.view_container)
        val penaltyContainer: ConstraintLayout = root.findViewById(R.id.penalty_container)

        logOut.setOnClickListener {
            /*val fm = fragmentManager
            val alertDialog = MyAlertDialogFragment.newInstance()
            alertDialog.show(fm!!, "fragment_alert")*/
            alertDialog()
        }

        this.penalty.setOnClickListener {
            alertDialogPenalty()
        }

        viewContainer.setOnClickListener {
            startActivity(Intent(context, ProfileActivity::class.java))
        }

        employeeList.setOnClickListener {
            startActivity(Intent(context, EmployeesListActivity::class.java))
        }

        when (myDataFromActivity) {
            1, 3 -> {
                employeeList.isVisible = false
                penaltyContainer.isVisible = false
            }
            2, 4 -> {
                penaltyContainer.isVisible = false
            }
            5 -> {
                employeeList.isVisible = false
            }
        }

        return root
    }
    private fun alertDialog(){
        val alertDialogBuilder = AlertDialog.Builder(context!!)
        alertDialogBuilder.setMessage(getString(R.string.log_out_desc))
        alertDialogBuilder.setPositiveButton(getString(R.string.yes), DialogInterface.OnClickListener { dialog, which ->
            // on success
            //setUserLoggedOut()
            // findNavController().popBackStack(R.id.loginFragment, false)
            /*var intent: Intent = Intent(context, AuthentificationActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            activity!!.finishAffinity()*/
            sendViewModel.logout()
            startActivity(Intent(context!!, AuthentificationActivity::class.java))
            activity?.finish()

        })
        alertDialogBuilder.setNegativeButton(getString(R.string.cancel_alert), DialogInterface.OnClickListener { dialog, which ->
            dialog?.dismiss()
        })

         alertDialogBuilder.show()
    }

    @SuppressLint("SetTextI18n")
    private fun alertDialogPenalty(){
        val alertDialogBuilder = androidx.appcompat.app.AlertDialog.Builder(context!!)
        alertDialogBuilder.setTitle(R.string.review_alert)
        val customLayout = layoutInflater.inflate(R.layout.arbitration_alert_dialog, null)
        customLayout.comment.inputType = InputType.TYPE_CLASS_NUMBER

        alertDialogBuilder.setView(customLayout)
        alertDialogBuilder.setMessage(getString(R.string.comment_action))

        alertDialogBuilder.setPositiveButton(getString(R.string.confirm)) { dialog, which ->
            val string = customLayout.comment.text
            val percent: String = resources.getString(R.string.percentage)
            this.penalty.text = percent + string

        }

        alertDialogBuilder.setNegativeButton(getString(R.string.cancel_alert)) { dialog, which ->
            Toast.makeText(context!!, "Cancelled", Toast.LENGTH_SHORT).show()
            dialog?.dismiss()
        }

        alertDialogBuilder.show()
    }
}