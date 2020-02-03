package com.example.profitclub.ui.consultant_individual_infos

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.PendingIntent.getActivity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.profitclub.MainActivity
import com.example.profitclub.R
import kotlinx.android.synthetic.main.fragment_client_individual_infos.*
import kotlinx.android.synthetic.main.fragment_client_individual_infos.date_of_birth
import kotlinx.android.synthetic.main.fragment_consultant_individual_info2.*
import kotlinx.android.synthetic.main.fragment_consultant_individual_infos.*
import kotlinx.android.synthetic.main.fragment_creation_account.*
import kotlinx.android.synthetic.main.fragment_login.*
import java.util.*

class ConsultantIndividualInfo2Fragment : Fragment(){

    private lateinit var toolsViewModel: ConsultantIndividualInfo2ViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        toolsViewModel =
            ViewModelProviders.of(this).get(ConsultantIndividualInfo2ViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_consultant_individual_info2, container, false)
        //val textView: TextView = root.findViewById(R.id.text_tools)
        /*toolsViewModel.text.observe(this, Observer {
            textView.text = it
        })*/

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*get_started_legal.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.splashScreeen2Action)
        }*/
        get_started_cons.setOnClickListener {
            val nextAction = ConsultantIndividualInfo2FragmentDirections.actionConsultantIndividualInfo2FragmentToSplashScreen2()
            nextAction.setRoleNum(3)
            Navigation.findNavController(it).navigate(nextAction)
        }
    }

    }

