package com.example.profitclub.ui

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.profitclub.LocaleManager
import com.example.profitclub.R
import com.example.profitclub.databinding.ActivityAuthentificationBinding
import com.example.profitclub.toast

class AuthentificationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthentificationBinding
    private lateinit var navController: NavController
    private lateinit var vm:AuthentificationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_authentification)
        navController = Navigation.findNavController(this, R.id.face1)
        vm = ViewModelProviders.of(this).get(AuthentificationViewModel::class.java)
    }

    @Override
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleManager.setLocale(base))
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onBackPressed() {
        val condition = (navController.currentDestination!!.id == R.id.slideshow) || (navController.currentDestination!!.id == R.id.share)
        if (condition) {
            super.onBackPressed()
        } else {
            toast("Please don't press... You might lost data")
        }
    }
}
