package com.example.profitclub.ui.email_check

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.profitclub.R
import com.example.profitclub.ui.create_account.CreationAcoountFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_email_check.*

class EmailCheckFragment : Fragment() {

    private lateinit var slideshowViewModel: EmailCheckViewModel
    private var role = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        slideshowViewModel =
            ViewModelProviders.of(this).get(EmailCheckViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_email_check, container, false)
        //val textView: TextView = root.findViewById(R.id.text_slideshow)
        /*slideshowViewModel.text.observe(this, Observer {
            textView.text = it
        })*/
            //email_check.text = arguments?.getString("role")
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val safeArgs = EmailCheckFragmentArgs.fromBundle(it)
            //email_check.text = "Role number: ${safeArgs.clientRole}"
            role = safeArgs.clientRole

        }
       // val role = arguments?.getString("role")

        confirm.setOnClickListener {

            if(role == 2) {
                Navigation.findNavController(it).navigate(R.id.clientLegalinfoAction)
            }
            if(role == 1){
                Navigation.findNavController(it).navigate(R.id.clientIndividualInfoAction)
                /*Snackbar.make(view, "No role", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()*/
            }
            if(role == 3){
                Navigation.findNavController(it).navigate(R.id.action_emailCheckFragment_to_consultantIndividualInfoFragment)
            }
            if(role == 4){
                Navigation.findNavController(it).navigate(R.id.action_emailCheckFragment_to_consultantLegalInfoFragment)
            }

        }
    }
}