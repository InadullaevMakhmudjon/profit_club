package com.example.profitclub.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.profitclub.R
import com.example.profitclub.databinding.ActivityAuthentificationBinding

class AuthentificationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthentificationBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_authentification)
        navController = Navigation.findNavController(this, R.id.face1)

    }

}
