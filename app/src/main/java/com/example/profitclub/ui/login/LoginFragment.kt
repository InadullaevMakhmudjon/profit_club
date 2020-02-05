package com.example.profitclub.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.profitclub.MainActivity
import com.example.profitclub.MainActivityViewModel
import com.example.profitclub.MainActivityViewModelFactory
import com.example.profitclub.R

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
                }
            })

            viewModel.error.observe(activity!!, Observer { error ->
                if(error.isNotEmpty()) {
                    Toast.makeText(activity!!, error, Toast.LENGTH_LONG).show()
                }
            })

            viewModel.loading.observe(activity!!, Observer {isLoading ->

            })
        }
    }
}