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
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.profitclub.MainActivity
import com.example.profitclub.R
import com.example.profitclub.data.BASE_URL
import com.example.profitclub.toast
import com.example.profitclub.ui.AuthentificationActivity
import com.example.profitclub.ui.account.details.ProfileActivity
import com.example.profitclub.ui.account.employees.EmployeesListActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.arbitration_alert_dialog.view.*
import kotlinx.android.synthetic.main.change_password_alert_dialog.view.*
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
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = activity as MainActivity?
        activity.let {
            activity?.customActionBarTitle(getString(R.string.Account))
        }

        val myDataFromActivity = activity!!.getMyData()
        preferences = context!!.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE)
        sendViewModel =
            ViewModelProviders.of(this, AccountViewModelFactory(preferences)).get(AccountViewModel::class.java)

        val logOut: Button = view.findViewById(R.id.log_out)
        val media = view.findViewById<ImageView>(R.id.avatar_user)
        val name = view.findViewById<TextView>(R.id.name_user)
        this.penalty = view.findViewById(R.id.num_penalty)
        val employeeList: ConstraintLayout = view.findViewById(R.id.employees_container)
        val viewContainer: ConstraintLayout = view.findViewById(R.id.view_container)
        val passwordContainer: ConstraintLayout = view.findViewById(R.id.changePassword_container)
        val penaltyContainer: ConstraintLayout = view.findViewById(R.id.penalty_container)

        val imageUser = preferences.getString("media_url", null)
        val nameUser = preferences.getString("lname", null)

        if (imageUser != null){
            Picasso.get().load(BASE_URL+imageUser).fit().into(media)
            name.text = nameUser
        }

        logOut.setOnClickListener {
            alertDialog()
        }

        passwordContainer.setOnClickListener {
            alertDialogChangePassword()
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
            2, 5 -> {
                employeeList.isVisible = false
                penaltyContainer.isVisible = false
            }
             1, 4, 7 -> {
                penaltyContainer.isVisible = false
            }
            6 -> {
                employeeList.isVisible = false
            }
        }
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
            dialog?.dismiss()
        }

        alertDialogBuilder.show()
    }

    private fun alertDialogChangePassword(){
        val alertDialogBuilder = androidx.appcompat.app.AlertDialog.Builder(context!!)
        alertDialogBuilder.setTitle(R.string.change_password)
        val customLayout = layoutInflater.inflate(R.layout.change_password_alert_dialog, null)

        alertDialogBuilder.setView(customLayout)

        alertDialogBuilder.setPositiveButton(getString(R.string.confirm)) { dialog, which ->
            val oldPassword = customLayout.old_password.text.toString()
            val newPassword = customLayout.new_password.text.toString()
            val passwordConfirmation = customLayout.password_confirmation.text.toString()
            if (oldPassword != "" && newPassword != "" && passwordConfirmation != ""){
                sendViewModel.changePassword(oldPassword, newPassword, passwordConfirmation)
            } else {
                toast(getString(R.string.all_fiels))
            }
            sendViewModel.status.observe(viewLifecycleOwner, Observer { data ->
                if (data.status == 0){
                    toast(getString(R.string.password_successfully))
                }
            })
        }

        alertDialogBuilder.setNegativeButton(getString(R.string.cancel_alert)) { dialog, which ->
            dialog?.dismiss()
        }

        alertDialogBuilder.show()
    }
}