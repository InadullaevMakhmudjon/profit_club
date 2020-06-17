package com.example.profitclub.ui

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.profitclub.LocaleManager
import com.example.profitclub.R
import com.example.profitclub.databinding.ActivityAuthentificationBinding

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
}
