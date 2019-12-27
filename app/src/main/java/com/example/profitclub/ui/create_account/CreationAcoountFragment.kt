package com.example.profitclub.ui.create_account

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.profitclub.MainActivity
import com.example.profitclub.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_creation_account.*
import kotlinx.android.synthetic.main.fragment_login.*

class CreationAcoountFragment : Fragment() {

    private lateinit var toolsViewModel: CreationAccountViewModel
    private var role = 1


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        toolsViewModel =
            ViewModelProviders.of(this).get(CreationAccountViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_creation_account, container, false)
        //val textView: TextView = root.findViewById(R.id.text_tools)
        /*toolsViewModel.text.observe(this, Observer {
            textView.text = it
        })*/

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val safeArgs = CreationAcoountFragmentArgs.fromBundle(it)
            role = safeArgs.role
           // create_text.text = role.toString()
        }
        onCheckboxClicked(view)

        create.setOnClickListener {
                /*if(role == 1 && legal_entity.isChecked){
                    Snackbar.make(view, "Legal_checked", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()

                    val emailAction = CreationAcoountFragmentDirections.emailCheckAction()
                    emailAction.setClientRole(2)
                    Navigation.findNavController(it).navigate(emailAction)

                } else{
                    Navigation.findNavController(it).navigate(R.id.emailCheckAction)
                }

            if(role == 2 && legal_entity.isChecked){
                val emailAction = CreationAcoountFragmentDirections.emailCheckAction()
                emailAction.setClientRole(2)
                Navigation.findNavController(it).navigate(emailAction)
            } else {
                val emailAction = CreationAcoountFragmentDirections.emailCheckAction()
                emailAction.setClientRole(3)
                Navigation.findNavController(it).navigate(emailAction)
            }*/
            val emailAction = CreationAcoountFragmentDirections.emailCheckAction()

            if(role == 1 && legal_entity.isChecked){
                emailAction.setClientRole(2)
            }
            if (role == 1 && individual.isChecked){
                emailAction.setClientRole(1)
            }
            if(role == 2 && legal_entity.isChecked){
                emailAction.setClientRole(4)
            }
            if (role == 2 && individual.isChecked){
                emailAction.setClientRole(3)
            }
            Navigation.findNavController(it).navigate(emailAction)

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

        /*when {
            individual.isChecked -> legal_entity.isEnabled = false
            legal_entity.isChecked -> individual.isEnabled = false
            else -> {
                individual.isEnabled = false
                legal_entity.isEnabled = false
            }
        }*/
    }
}