package com.example.profitclub.ui.client_individual_infos.client_legal_infos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.profitclub.R
import kotlinx.android.synthetic.main.fragment_client_legal_info2.*

class ClientLegalInfo2Fragment : Fragment(){

    private lateinit var toolsViewModel: ClientLegalInfo2ViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        toolsViewModel =
            ViewModelProviders.of(this).get(ClientLegalInfo2ViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_client_legal_info2, container, false)
        //val textView: TextView = root.findViewById(R.id.text_tools)
        /*toolsViewModel.text.observe(this, Observer {
            textView.text = it
        })*/

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        get_started_legal.setOnClickListener {
            val role = 2
            //val client_legal_action = ClientLegalInfo2FragmentDirections
            val client_legal_action = ClientLegalInfo2FragmentDirections.actionClientLegalInfo2ToSplashScreen2()
            client_legal_action.setRoleNum(role)
            Navigation.findNavController(it).navigate(client_legal_action)
        }
    }

    }

