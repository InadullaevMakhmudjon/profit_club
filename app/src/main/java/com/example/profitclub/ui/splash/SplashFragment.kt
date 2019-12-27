package com.example.profitclub.ui.splash

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.profitclub.R
import kotlinx.android.synthetic.main.activity_splash_screen.*
import kotlinx.android.synthetic.main.fragment_splash.*
import kotlinx.android.synthetic.main.fragment_splash.account
import kotlinx.android.synthetic.main.fragment_splash.fullscreen_content
import kotlinx.android.synthetic.main.fragment_splash.fullscreen_content_controls

class SplashFragment : Fragment() {

    private lateinit var shareViewModel: ShareViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        shareViewModel =
            ViewModelProviders.of(this).get(ShareViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_splash, container, false)
        //val textView: TextView = root.findViewById(R.id.text_share)

       // var bundle = Bundle()
       // val navController = findNavController()

        shareViewModel.text.observe(this, Observer {
           // textView.text = it
        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        create_button.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.optionAction)
        }

        account.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.loginAction)
        }
    }
}