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
import kotlinx.android.synthetic.main.fragment_consultant_individual_infos.*
import kotlinx.android.synthetic.main.fragment_creation_account.*
import kotlinx.android.synthetic.main.fragment_login.*
import java.util.*

class ConsultantIndividualInfoFragment : Fragment(), DatePickerDialog.OnDateSetListener  {

    private lateinit var toolsViewModel: ConsultantIndividualInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        toolsViewModel =
            ViewModelProviders.of(this).get(ConsultantIndividualInfoViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_consultant_individual_infos, container, false)
        //val textView: TextView = root.findViewById(R.id.text_tools)
        /*toolsViewModel.text.observe(this, Observer {
            textView.text = it
        })*/

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        date_of_birth.setOnClickListener {
            showDatePickerDialog()
        }

        next.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_consultantIndividualInfoFragment_to_consultantIndividualInfo2Fragment)
        }
    }

    private fun showDatePickerDialog() {
        var datePickerDialog = context?.let {
            DatePickerDialog(
                it,this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH))
        }
        if (datePickerDialog != null) {
            datePickerDialog.show()
        }
    }

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
            val date = "Date of birth: " + p1 + "/" + p2 + "/" + p3;
            date_of_birth.text = date
        }
    }

