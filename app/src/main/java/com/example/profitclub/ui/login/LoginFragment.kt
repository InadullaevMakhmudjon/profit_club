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
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.profitclub.*

class LoginFragment : Fragment() {
    private val APP_PREFERENCE = "MYSETTINGS"

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

        activity?.let {
            val preferences = it.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE)
            val viewModel = ViewModelProviders.of(it, MainActivityViewModelFactory(preferences)).get(MainActivityViewModel::class.java)

            login.setOnClickListener {
                viewModel.login(email.text.toString(), password.text.toString())
            }

            viewModel.token.observe(activity!!, Observer { token ->
                if(token != null) {
                    startActivity(Intent(activity, MainActivity::class.java))
                    activity!!.finish();
                }
            })

            viewModel.status.observe(activity!!, Observer { status ->
                if (status == 3){
                    alertDialogInfo()
                }
            })

            viewModel.error.observe(activity!!, Observer { error ->
                if(error.isNotEmpty()) {
                    toast("$error")
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

    fun checkPermission() {
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
            // If request is cancelled, the result arrays are empty.
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                // permission was granted, yay!
                callPhone()
            } else {
                // permission denied, boo! Disable the
                // functionality
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