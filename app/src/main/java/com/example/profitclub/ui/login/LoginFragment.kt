package com.example.profitclub.ui.login

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.profitclub.*
import kotlinx.android.synthetic.main.arbitration_alert_dialog.view.*
import kotlinx.android.synthetic.main.change_password_alert_dialog.view.*
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {
    private val APP_PREFERENCE = "MYSETTINGS"
    private var key: Int = 0
    var step: Int? = 0
    private var email_: String? = null
    private var hash: String? = null
    private lateinit var viewModel: MainActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val email = view.findViewById<AutoCompleteTextView>(R.id.email)
        val password = view.findViewById<AutoCompleteTextView>(R.id.password)
        val login = view.findViewById<Button>(R.id.login)
        val layoutMain = view.findViewById<ScrollView>(R.id.layout_main)
        val loading = view.findViewById<RelativeLayout>(R.id.loading_dots)

        activity?.let {
            val preferences = it.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE)
            viewModel = ViewModelProviders.of(it, MainActivityViewModelFactory(preferences)).get(MainActivityViewModel::class.java)

            forgot_password.setOnClickListener {
                password_container.isVisible = false
                forgot_password.isVisible = false
                log_in.text = getString(R.string.forgot)
                login.text = getString(R.string.next)
                key = 1
            }

            login.setOnClickListener {
                when (key){
                    1 -> {
                        email_ = email.text.toString()
                        if (email_ != ""){
                            viewModel.resetPasswordStep1(email.text.toString(), 1, "en")
                        }
                    }
                    0 -> {
                        if (email.text.toString() != "" && password.text.toString() != ""){
                            viewModel.login(email.text.toString(), password.text.toString())

                            viewModel.status.observe(activity!!, Observer { status ->
                                when (status){
                                    1 -> toast(getString(R.string.incorrect_email))
                                    2 -> toast(getString(R.string.blocked_account))
                                    3 -> alertDialogInfo()
                                }
                            })
                        }
                    }
                }

                viewModel.loading.observe(viewLifecycleOwner, Observer { status ->
                    if (status == true){
                        layoutMain.hide()
                        loading.show()
                    } else {
                        layoutMain.show()
                        loading.hide()
                    }
                })

                viewModel.step1Status.observe(viewLifecycleOwner, Observer { data1 ->
                    if (data1 != null) {
                        if (data1.status){
                            alertReset2()
                        } else {
                            toast(getString(R.string.account_not_verified))
                        }
                    }
                })
            }

            viewModel.step2Status.observe(viewLifecycleOwner, Observer { data2 ->
                if (data2 != null) {
                    if (data2.status){
                        alertReset3()
                    }
                }
            })

            viewModel.step3Status.observe(viewLifecycleOwner, Observer { data3 ->
                if (data3 != null) {
                    if (data3.status){
                        key = 0
                        password_container.isVisible = true
                        forgot_password.isVisible = true
                        log_in.text = getString(R.string.login)
                        login.text = getString(R.string.action_sign_in)
                    }
                }
            })

            viewModel.token.observe(activity!!, Observer { token ->
                if(token != null) {
                    startActivity(Intent(activity, MainActivity::class.java))
                    activity!!.finish()
                }
            })

            viewModel.error.observe(activity!!, Observer { error ->
                if(error.isNotEmpty()) {
                    toast(error)
                }
            })

            viewModel.loading.observe(activity!!, Observer {isLoading ->

            })
        }
    }

    private fun alertDialogInfo(){
        val alertDialogBuilder = AlertDialog.Builder(activity)
        val customLayout = layoutInflater.inflate(R.layout.consultant_alert, null)
        alertDialogBuilder.setView(customLayout)

        alertDialogBuilder.setPositiveButton(getString(R.string.call)) { dialog, which ->
            checkPermission()
        }

        alertDialogBuilder.setNegativeButton(getString(R.string.cancel_alert)) { dialog, which ->
            dialog?.cancel()
        }

        alertDialogBuilder.show()
    }

    private fun alertReset2(){
        val alertDialogBuilder = AlertDialog.Builder(activity)
        val customLayout = layoutInflater.inflate(R.layout.arbitration_alert_dialog, null)
        alertDialogBuilder.setView(customLayout)
        alertDialogBuilder.setTitle(getString(R.string.email_check))
        customLayout.comment.hint = getString(R.string.confirmation_code)

        alertDialogBuilder.setPositiveButton(getString(R.string.confirm)) { dialog, which ->
            hash = customLayout.comment.text.toString()
            if (hash != ""){
                viewModel.resetPasswordStep2(email_!!, hash!!, 2, "en")
            }
            dialog?.cancel()
        }

        alertDialogBuilder.setNegativeButton(getString(R.string.cancel_alert)) { dialog, which ->
            dialog?.cancel()
        }
        alertDialogBuilder.show()
    }

    private fun alertReset3(){
        val alertDialogBuilder = AlertDialog.Builder(activity)
        val customLayout = layoutInflater.inflate(R.layout.change_password_alert_dialog, null)
        alertDialogBuilder.setView(customLayout)
        customLayout.old_password_container.isVisible = false

        alertDialogBuilder.setPositiveButton(getString(R.string.confirm)) { dialog, which ->
            val password = customLayout.new_password.text.toString()
            val passwordRepeat = customLayout.password_confirmation.text.toString()
            if (password != "" && passwordRepeat != "" && password == passwordRepeat){
                viewModel.resetPasswordStep3(email_!!, hash!!, password, passwordRepeat, 3, "en")
            }
            dialog?.cancel()
        }

        alertDialogBuilder.setNegativeButton(getString(R.string.cancel_alert)) { dialog, which ->
            dialog?.cancel()
        }

        alertDialogBuilder.show()
    }

    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (ContextCompat.checkSelfPermission(activity!!, android.Manifest.permission.CALL_PHONE) ==
                PackageManager.PERMISSION_DENIED){
                //permission denied
                val permissions = arrayOf(android.Manifest.permission.CALL_PHONE);
                //show popup to request runtime permission
                requestPermissions(permissions, 42);
            }
            else{
                //permission already granted
                callPhone()
            }
        }
        else{
            //system OS is < Marshmallow
            callPhone();
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == 42) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                callPhone()
            } else {
                Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
            }
            return
        }
    }

    private fun callPhone(){
        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "+998732002427"))
        startActivity(intent)
    }
}