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
import com.example.profitclub.*
import kotlinx.android.synthetic.main.activity_splash_screen.*
import kotlinx.android.synthetic.main.fragment_splash.*
import kotlinx.android.synthetic.main.fragment_splash.account
import kotlinx.android.synthetic.main.fragment_splash.fullscreen_content
import kotlinx.android.synthetic.main.fragment_splash.fullscreen_content_controls
import kotlinx.android.synthetic.main.no_internet_layout.*

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

        chooseLanguage()

        retryClick()

        create_button.setOnClickListener {
            if (isNetworkAvailable())
            Navigation.findNavController(it).navigate(R.id.optionAction)
            else {
                frame_layout.hide()
                layout_no_connection.show()
            }
        }

        account.setOnClickListener {
            if(isNetworkAvailable())
            Navigation.findNavController(it).navigate(R.id.loginAction)
            else
                frame_layout.hide()
            layout_no_connection.show()
        }
    }

    private fun retryClick() {
        retry.setOnClickListener {
            if (isNetworkAvailable()){
                layout_no_connection.hide()
                frame_layout.show()
                //requestIfUserAlreadyLoggedIn()
            }
            else
                toast(getString(R.string.connect_to_internet))
        }
    }

    override fun onResume() {
        val icon = getLanguageDrawable(context?.getLanguage()!!)
        choose_language.setImageDrawable(icon)
        super.onResume()
    }
}