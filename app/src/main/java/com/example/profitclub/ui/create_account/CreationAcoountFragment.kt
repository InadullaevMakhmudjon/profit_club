package com.example.profitclub.ui.create_account

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.profitclub.MainActivity
import com.example.profitclub.R
import com.example.profitclub.toast
import com.example.profitclub.ui.account.AccountViewModelFactory
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.fragment_creation_account.*
import kotlinx.android.synthetic.main.fragment_login.*

class CreationAcoountFragment : Fragment() {

    private var role = 1

    private lateinit var preferences: SharedPreferences
    private val APP_PREFERENCE = "MYSETTINGS"


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_creation_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val email = view.findViewById<AutoCompleteTextView>(R.id.email)
        val password = view.findViewById<AutoCompleteTextView>(R.id.password)
        val passwordRepeat = view.findViewById<AutoCompleteTextView>(R.id.password_repeat)
        val create = view.findViewById<Button>(R.id.create)

        arguments?.let {
            val safeArgs = CreationAcoountFragmentArgs.fromBundle(it)
            role = safeArgs.role
            // create_text.text = role.toString()
        }

        activity?.let { it ->
            preferences = it.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE)
            val viewModel = ViewModelProviders.of(it, CreationAccountViewModelFactory(preferences)).get(CreationAccountViewModel::class.java)
            val emailAction = CreationAcoountFragmentDirections.emailCheckAction()

            if(role == 1 && legal_entity.isChecked){
                emailAction.setClientRole(7)
            }
            if (role == 1 && individual.isChecked){
                emailAction.setClientRole(5)
            }
            if(role == 2 && legal_entity.isChecked){
                emailAction.setClientRole(4)
            }
            if (role == 2 && individual.isChecked){
                emailAction.setClientRole(2)
            }
            create.setOnClickListener {
                if(password.text.toString() == password_repeat.text.toString() && individual.isChecked ||
                    legal_entity.isChecked){
                    viewModel.register(email.text.toString(), password.text.toString(),
                        passwordRepeat.text.toString(), role)
                } else {
                    Snackbar.make(it, "Check one of checkboxes only", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
                }
            }

            viewModel.error.observe(activity!!, Observer { error ->
                if(error.isNotEmpty()) {
                    Toast.makeText(activity!!, error, Toast.LENGTH_LONG).show()
                }
            })

            viewModel.loading.observe(activity!!, Observer {isLoading ->

            })

            viewModel.status.observe(activity!!, Observer { status ->
                viewModel.loginId.value?.toInt()?.let { it1 -> emailAction.setLoginId(it1) }
                when (status) {
                    0 -> Navigation.findNavController(create).navigate(emailAction)
                    -1 -> toast("The email already already registered")
                    1 -> toast("The account not activated")
                    2 -> toast("The account not verified")
                    3 -> toast("Info about user not completed")
                    5 -> toast("Password does not match with repeated password")
                    9 -> toast("Server error")
                    else -> toast("Async is not implemented")
                }
            })
        }
    }

    private fun onCheckboxClicked(v: View){
        val checkBox: CheckBox = v.findViewById(R.id.individual) as CheckBox
        val checkBox2: CheckBox = v.findViewById(R.id.legal_entity) as CheckBox
        when(v.id){
            R.id.individual -> if(checkBox.isChecked){
                checkBox2.isEnabled = false
            } else{
                checkBox.isEnabled = true
                checkBox2.isEnabled = true
            }

            R.id.legal_entity -> if(checkBox2.isChecked){
                checkBox.isEnabled = false
            } else {
                checkBox2.isEnabled = true
                checkBox.isEnabled = true
            }
        }
    }
}