package com.example.profitclub.ui.email_check

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.profitclub.R

class EmailCheckFragment : Fragment() {

    private lateinit var viewModel: EmailCheckViewModel
    private var role = 1

    private lateinit var preferences: SharedPreferences
    private val APP_PREFERENCE = "MYSETTINGS"
    var loginId: Int? = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_email_check, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val emailCheck = view.findViewById<TextView>(R.id.email_check)
        val timerText = view.findViewById<TextView>(R.id.timer)
        val confirm = view.findViewById<Button>(R.id.confirm)
        val emailConfirm = view.findViewById<AutoCompleteTextView>(R.id.confirm_email)

        val timer = object: CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
               timerText.text = (millisUntilFinished/1000).toString()
            }

            override fun onFinish() {
                timerText.text = "0"
            }
        }
        timer.start()

        arguments?.let {
            val safeArgs = EmailCheckFragmentArgs.fromBundle(it)
            //email_check.text = "Role number: ${safeArgs.clientRole}"
            role = safeArgs.clientRole
            //emailCheck.text = safeArgs.loginId.toString()
            loginId = safeArgs.loginId
    }

        activity?.let { it ->
            preferences = it.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE)
            viewModel = ViewModelProviders.of(this, EmailCheckViewModelFactory(preferences)).get(EmailCheckViewModel::class.java)

            val userInfoAction = EmailCheckFragmentDirections.clientIndividualInfoAction()
            confirm.setOnClickListener {
                viewModel.emailVerify(emailConfirm.text.toString())
                /*if (viewModel.status.value == true){

                    when (role) {
                        7 -> Navigation.findNavController(it).navigate(R.id.clientLegalinfoAction)
                        5 -> Navigation.findNavController(it).navigate(R.id.clientIndividualInfoAction)
                        4 -> Navigation.findNavController(it).navigate(R.id.action_emailCheckFragment_to_consultantLegalInfoFragment)
                        2 ->  Navigation.findNavController(it).navigate(R.id.action_emailCheckFragment_to_consultantIndividualInfoFragment)
                    }
                } else{
                    toast("Your TOKEN did not match!")
                }*/
            }

            viewModel.error.observe(activity!!, Observer { error ->
                if(error.isNotEmpty()) {
                    Toast.makeText(activity!!, error, Toast.LENGTH_LONG).show()
                }
            })

            viewModel.loading.observe(activity!!, Observer {isLoading ->

            })

            viewModel.status.observe(activity!!, Observer { status ->
                loginId?.let { it1 -> userInfoAction.setLoginId(it1) }
                when(status){
                    true -> Navigation.findNavController(confirm).navigate(userInfoAction)
                }
            })
        }
    }
}