package com.example.profitclub.ui.create_account

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.profitclub.R
import com.example.profitclub.toast
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_creation_account.*

class CreationAcoountFragment : Fragment() {
    private var role: Int = 0
    private lateinit var preferences: SharedPreferences
    private val APP_PREFERENCE = "MYSETTINGS"
    private var emailText: String? = null
    private var passwordText: String? = null
    private var passwordRepeatText: String? = null

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
        val individual = view.findViewById<CheckBox>(R.id.individual)
        val individualText = view.findViewById<TextView>(R.id.individual_text)
        val legalEntity = view.findViewById<CheckBox>(R.id.legal_entity)
        val legalEntityText = view.findViewById<TextView>(R.id.legal_entity_text)

        individual.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                individualText.setTextColor(resources.getColor(android.R.color.black))
                legalEntity.isChecked = false
                // Code to display your message.
            } else
                individualText.setTextColor(resources.getColor(android.R.color.darker_gray))
        }

        legalEntity.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                individualText.setTextColor(resources.getColor(android.R.color.black))
                individual.isChecked = false
                // Code to display your message.
            } else
                legalEntityText.setTextColor(resources.getColor(android.R.color.darker_gray))
        }

        arguments?.let {
            val safeArgs = CreationAcoountFragmentArgs.fromBundle(it)
            role = safeArgs.role
            // create_text.text = role.toString()
        }

        activity?.let { it ->
            preferences = it.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE)
            val viewModel = ViewModelProviders.of(it, CreationAccountViewModelFactory(preferences)).get(CreationAccountViewModel::class.java)
            val emailAction = CreationAcoountFragmentDirections.emailCheckAction()
            val infoAction = CreationAcoountFragmentDirections.infoAction()
            create.setOnClickListener {
                if(role == 1 && legal_entity.isChecked){
                    role = 7
                    emailAction.setClientRole(role)
                }
                if (role == 1 && individual.isChecked){
                    role = 5
                    emailAction.setClientRole(role)
                }
                if(role == 2 && legal_entity.isChecked){
                    role = 4
                    emailAction.setClientRole(role)
                }
                if (role == 2 && individual.isChecked){
                    role = 2
                    emailAction.setClientRole(role)
                }

                if (individual.isChecked || legal_entity.isChecked){
                    emailText = email.text.toString()
                    passwordText = password.text.toString()
                    passwordRepeatText = passwordRepeat.text.toString()
                    if (emailText != "" && passwordText != "" && passwordRepeatText != ""){
                        viewModel.register(email.text.toString(), password.text.toString(),
                            passwordRepeat.text.toString(), role)
                    } else {
                        Snackbar.make(it, context!!.getString(R.string.all_fiels), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show()
                    }
                }  else {
                    Snackbar.make(it, context!!.getString(R.string.check_box), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
                }
            }

            viewModel.error.observe(activity!!, Observer { error ->
                if(error.isNotEmpty()) {
                    Toast.makeText(activity!!, error, Toast.LENGTH_LONG).show()
                }
            })

            viewModel.loading.observe(activity!!, Observer { isLoading ->

            })

            viewModel.status.observe(activity!!, Observer { status ->

                when (status) {
                    0, 2 -> {
                        val editor = preferences.edit()
                        editor.putString("email", emailText)
                        editor.putString("password", passwordText)
                        editor.putString("password_repeat", passwordRepeatText)
                        editor.putInt("type_t", role)
                        editor.apply()
                        viewModel.loginId.value?.toInt()?.let { it1 -> emailAction.setLoginId(it1) }
                        Navigation.findNavController(create).navigate(emailAction)
                    }
                    -1 -> toast(getString(R.string.mail_already_registered))
                    1 -> toast(getString(R.string.account_not_activated))
                    3 -> {
                        viewModel.loginId.value?.toInt()?.let { it1 -> infoAction.setLoginId(it1) }
                        Navigation.findNavController(create).navigate(infoAction)
                        toast(getString(R.string.info_not_completed))
                    }
                    5 -> toast(getString(R.string.password_does_not_match))
                    9 -> toast(getString(R.string.server_error))
                }
            })
        }
    }
}