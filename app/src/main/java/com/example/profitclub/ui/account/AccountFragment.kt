package com.example.profitclub.ui.account

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.profitclub.R
import com.example.profitclub.ui.AuthentificationActivity
import kotlinx.android.synthetic.main.fragment_account.*

class AccountFragment : Fragment() {

    private lateinit var sendViewModel: AccountViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sendViewModel =
            ViewModelProviders.of(this).get(AccountViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_account, container, false)
        //val textView: TextView = root.findViewById(R.id.text_send)
        /*sendViewModel.text.observe(this, Observer {
           // textView.text = it
        })*/
        val logOut: TextView = root.findViewById(R.id.log_out)
        logOut.setOnClickListener {
            var intent: Intent = Intent(context, AuthentificationActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

        return root
    }
}