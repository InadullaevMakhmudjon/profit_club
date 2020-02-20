package com.example.profitclub.ui.client_individual_infos

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.profitclub.MainActivity
import com.example.profitclub.R
import kotlinx.android.synthetic.main.fragment_client_verification.*
import kotlinx.android.synthetic.main.fragment_login.*

class ClientVerificationFragment : Fragment() {
    private var role = 1
    //private lateinit var toolsViewModel: ClientIndividualInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
      //  toolsViewModel =
         //   ViewModelProviders.of(this).get(ClientIndividualInfoViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_client_verification, container, false)
        //val textView: TextView = root.findViewById(R.id.text_tools)
        /*toolsViewModel.text.observe(this, Observer {
            textView.text = it
        })*/
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

                arguments?.let {
                    val safeArgs = ClientVerificationFragmentArgs.fromBundle(it)
                    this.role = safeArgs.roleNum
                }
        /*if(role == 2 || role ==4){
            verification_text.isVisible = false
            passport_container.isVisible = false
        }*/

        verify.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            //intent.putExtra("role", role)
            val data = Bundle()
            data.putInt("role", role)
            intent.putExtras(data)
            startActivity(intent)
            activity?.finish()
        }
    }
}