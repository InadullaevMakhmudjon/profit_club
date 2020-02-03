package com.example.profitclub

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import com.example.profitclub.ui.AuthentificationActivity
import kotlinx.android.synthetic.main.activity_chat_view.*

class MyAlertDialogFragment : DialogFragment(){

    companion object{
        fun newInstance(): MyAlertDialogFragment {
            return MyAlertDialogFragment()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val alertDialogBuilder = AlertDialog.Builder(context!!)
        alertDialogBuilder.setMessage(getString(R.string.log_out_desc))
        alertDialogBuilder.setPositiveButton(getString(R.string.yes), DialogInterface.OnClickListener { dialog, which ->
            // on success
            //setUserLoggedOut()
           // findNavController().popBackStack(R.id.loginFragment, false)
            var intent: Intent = Intent(context, AuthentificationActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

            startActivity(intent)
        })
        alertDialogBuilder.setNegativeButton(getString(R.string.cancel), DialogInterface.OnClickListener { dialog, which ->
            dialog?.dismiss()
        })

        return alertDialogBuilder.create()
    }

}